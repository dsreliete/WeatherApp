package com.elieterodrigues.weatherappkotlin.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toolbar
import com.elieterodrigues.weatherappkotlin.R
import com.elieterodrigues.weatherappkotlin.domain.RequestForecastCommand
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), ToolbarManager {

    private val zipCode: Long by DelegatesExt.preference(this,
            SettingsActivity.ZIP_CODE, SettingsActivity.DEFAULT_ZIP)

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        forecast_list.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecast_list)
    }

    private fun loadForecast() = doAsync {
        val result = RequestForecastCommand(zipCode).execute()
        uiThread {
            val adapter = ForecastListAdapter(result) {
                forecast ->
                startActivity<DetailActivity>(DetailActivity.ID to forecast.id,
                        DetailActivity.CITY_NAME to result.city)
            }
            forecast_list.adapter = adapter
            toolbarTitle = "${result.city} (${result.country})"
        }
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }
}

