package com.elieterodrigues.weatherappkotlin.domain

interface ForecastDataSource {

    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long): ModelForecast?

}