package com.elieterodrigues.weatherappkotlin.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.elieterodrigues.weatherappkotlin.R
import com.elieterodrigues.weatherappkotlin.domain.ForecastList
import com.elieterodrigues.weatherappkotlin.domain.ModelForecast
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastListAdapter(private val weekForecast: ForecastList,
                          private val itemClick: (ModelForecast) -> Unit)
    : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val view = parent.inflate(R.layout.item_forecast)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(weekForecast[position])
    }

    override fun getItemCount(): Int = weekForecast.size
}

class ViewHolder(view: View, val itemClick: (ModelForecast) -> Unit) : RecyclerView.ViewHolder(view) {

    private val iconView = view.iconItem
    private val dateView = view.date
    private val descriptionView = view.description
    private val maxTemperatureView = view.maxTemperature
    private val minTemperatureView = view.minTemperature

    fun bindForecast(forecast: ModelForecast) {

//        with receives an object, and a function that will be executed as an extension function.
// This means we can use this inside the function to refer to the object. It will also return an
// object defined in the last line of the function.

//        inline fun <T, R> with(receiver: T, f: T.() -> R): R = receiver.f()

// Generics work the same way here: T stands for the receiver type, and R for the result.
// As you can see, the function is defined as an extension function by using this declaration:
// f: T.() -> R. That’s why we can then call receiver.f().

        with(forecast){
            iconView.loadIconUrl(iconUrl)
            dateView.text = date.toDateString()
            descriptionView.text = description
            maxTemperatureView.text = "$high"
            minTemperatureView.text = "$low"
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}
