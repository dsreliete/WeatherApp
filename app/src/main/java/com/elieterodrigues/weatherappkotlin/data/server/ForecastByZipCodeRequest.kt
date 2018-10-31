package com.elieterodrigues.weatherappkotlin.data.server

import android.util.Log
import com.google.gson.Gson
import java.net.URL

class ForecastByZipCodeRequest(val zipCode : Long) {
//http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7&appid=672ea5ff0fcafdc4b07a54a4dfe12f95&zip=21231,us

    companion object {
        private val APP_ID = "672ea5ff0fcafdc4b07a54a4dfe12f95"
        private val URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7"
    }

    fun execute() : ForecastResult {
        val completeUrl = "$URL&appid=$APP_ID&zip=$zipCode,us"
        val forecastResult = URL(completeUrl).readText()
        Log.e("request", forecastResult)
        return Gson().fromJson(forecastResult, ForecastResult::class.java)
    }
}