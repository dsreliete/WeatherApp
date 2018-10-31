package com.elieterodrigues.weatherappkotlin.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toolbar
import android.widget.TextView
import com.elieterodrigues.weatherappkotlin.R
import com.elieterodrigues.weatherappkotlin.domain.ModelForecast
import com.elieterodrigues.weatherappkotlin.domain.RequestDayForecastCommand
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import java.text.DateFormat

class DetailActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    companion object {
        const val ID = "DetailActivity:id"
        const val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initToolbar()
        toolbarTitle = intent.getStringExtra(CITY_NAME)
        enableHomeAsUp {
            onBackPressed()
        }

        val forecastId = intent.getLongExtra(ID, -1)

        doAsync {
            val result = RequestDayForecastCommand(forecastId).execute()
            uiThread { bindForecast(result) }
        }
    }

    private fun bindForecast(forecast: ModelForecast) = with(forecast) {
        supportActionBar?.subtitle = date.toDateString(DateFormat.FULL)
        iconDetail.loadIconUrl(iconUrl)
        weatherDescription.text = description
        bindWeather(high to maxTemp, low to minTemp)
    }

    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = "${it.first}"
        it.second.textColor = color(when (it.first) {
            in -50..0 -> android.R.color.holo_red_dark
            in 0..15 -> android.R.color.holo_orange_dark
            else -> android.R.color.holo_green_dark
        })
    }
}



