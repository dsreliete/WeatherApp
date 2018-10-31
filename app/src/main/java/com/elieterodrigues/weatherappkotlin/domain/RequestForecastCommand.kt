package com.elieterodrigues.weatherappkotlin.domain

import android.util.Log
import com.elieterodrigues.weatherappkotlin.data.server.ForecastByZipCodeRequest

class RequestForecastCommand(val zipCode: Long,
                             val forecastProvider: ForecastProvider = ForecastProvider()) : Command<ForecastList> {

    companion object { val DAYS = 7 }

    override fun execute(): ForecastList {
        return forecastProvider.requestByZipCode(zipCode, DAYS)
    }

}