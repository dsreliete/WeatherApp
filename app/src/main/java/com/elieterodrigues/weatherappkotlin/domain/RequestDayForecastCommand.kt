package com.elieterodrigues.weatherappkotlin.domain

class RequestDayForecastCommand( val id : Long,
                                 val forecastProvider: ForecastProvider = ForecastProvider())
                                : Command<ModelForecast> {
    override fun execute() = forecastProvider.requestForecast(id)
}