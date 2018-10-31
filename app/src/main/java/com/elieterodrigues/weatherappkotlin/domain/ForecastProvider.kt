package com.elieterodrigues.weatherappkotlin.domain

import com.elieterodrigues.weatherappkotlin.data.db.ForecastDB
import com.elieterodrigues.weatherappkotlin.data.server.ForecastServer

class ForecastProvider(private val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {

    companion object {
        const val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES by lazy { listOf(ForecastDB(), ForecastServer()) }
    }

    fun requestForecast(id: Long): ModelForecast = requestToSources {
        it.requestDayForecast(id)
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList
            = sources.firstResult{ requestSource(it, days, zipCode) }


    private fun requestSource(source: ForecastDataSource, days: Int, zipCode: Long): ForecastList? {
        val res = source.requestForecastByZipCode(zipCode, todayTimeSpan())
        return if (res != null && res.size >= days) res else null
    }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    //It will receive a function which uses a ForecastDataSource to return a nullable object of
// the generic type, and will finally return a non-nullable object.
    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T = sources.firstResult { f(it) }
}