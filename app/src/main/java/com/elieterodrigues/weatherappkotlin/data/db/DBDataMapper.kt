package com.elieterodrigues.weatherappkotlin.data.db

import com.elieterodrigues.weatherappkotlin.domain.ForecastList
import com.elieterodrigues.weatherappkotlin.domain.ModelForecast

class DBDataMapper {


    fun convertFromDomain(forecast: ForecastList) = with(forecast) {
        val daily = dailyForecast.map { convertDayFromDomain(id, it) }
        CityForecast(id, city, country, daily)
    }

    private fun convertDayFromDomain(cityId: Long, forecast: ModelForecast) = with(forecast) {
        DayForecast(date, description, high, low, iconUrl, cityId)
    }

    fun convertToDomain(forecast: CityForecast) = with(forecast) {
        val daily = dailyForecast.map { convertDayToDomain(it) }
        ForecastList(_id, city, country, daily)
    }

    fun convertDayToDomain(dayForecast: DayForecast) = with(dayForecast) {
        ModelForecast(_id, date, description, high, low, iconUrl)
    }

}