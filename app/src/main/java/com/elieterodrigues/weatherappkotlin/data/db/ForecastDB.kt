package com.elieterodrigues.weatherappkotlin.data.db

import com.elieterodrigues.weatherappkotlin.domain.ForecastDataSource
import com.elieterodrigues.weatherappkotlin.domain.ForecastList
import com.elieterodrigues.weatherappkotlin.domain.ModelForecast
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

// parselist : So, to understand what is happening behind the scenes, the request returns a Cursor.
// parseList iterates over it and gets the rows from the Cursor until it reaches the last one.
// For each row, it creates a map with the columns as keys and assigns the value to the corresponding key.
// The map is then returned to the parser.

//  parseOpt:  This function returns a nullable object.
//  The result can be null or a single object, depending on whether the request finds something
//  in the database or not.

class ForecastDB(private val forecastDbHelper: ForecastDBHelper = ForecastDBHelper.instance,
                 private val dataMapper: DBDataMapper = DBDataMapper()) : ForecastDataSource {


    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {

        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"

        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                .parseList { DayForecast(HashMap(it)) }


        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt { CityForecast(HashMap(it), dailyForecast) }

// It receives a function that will take the object as a parameter, and returns the value that this function returns
// It uses two generic types: T and R. The first one is defined by the calling object, and
// it’s the type that the function receives. The second one is the result of the function.

//        inline fun <T, R> T.let(f: (T) -> R): R = f(this)

        city?.let {
            dataMapper.convertToDomain(it)
        }
    }

    override fun requestDayForecast(id: Long): ModelForecast? = forecastDbHelper.use {
        val forecast = select(DayForecastTable.NAME).byId(id).
            parseOpt { DayForecast(HashMap(it)) }

        forecast?.let{
                dataMapper.convertDayToDomain(it)
            }
    }

    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        val forecastDBModel = dataMapper.convertFromDomain(forecast)
        with(forecastDBModel) {
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach { insert(DayForecastTable.NAME, *it.map.toVarargArray()) }
        }
    }

}