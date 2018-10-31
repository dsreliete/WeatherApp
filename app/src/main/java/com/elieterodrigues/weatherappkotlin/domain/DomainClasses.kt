package com.elieterodrigues.weatherappkotlin.domain

data class ForecastList(val id: Long, val city: String, val country: String,
                        val dailyForecast: List<ModelForecast>){

    val size: Int
        get() = dailyForecast.size

    operator fun get(position: Int): ModelForecast = dailyForecast[position]
}

data class ModelForecast(val id: Long, val date: Long, val description: String, val high: Int,
                         val low: Int, val iconUrl : String)