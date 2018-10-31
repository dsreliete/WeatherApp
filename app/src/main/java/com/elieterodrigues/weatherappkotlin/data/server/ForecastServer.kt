package com.elieterodrigues.weatherappkotlin.data.server

import com.elieterodrigues.weatherappkotlin.data.db.ForecastDB
import com.elieterodrigues.weatherappkotlin.domain.ForecastDataSource
import com.elieterodrigues.weatherappkotlin.domain.ForecastList

class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper(),
                     private val forecastDb: ForecastDB = ForecastDB()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val converted = dataMapper.convertToDomain(zipCode, result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }

    override fun requestDayForecast(id: Long) = throw UnsupportedOperationException()
}

