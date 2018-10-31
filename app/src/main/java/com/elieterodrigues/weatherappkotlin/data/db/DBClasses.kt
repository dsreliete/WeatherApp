package com.elieterodrigues.weatherappkotlin.data.db

//Sometimes we need something else to initialise a property, but we don’t have the required state
// available in the constructor, or we are even not able to access to them.
//Another way to delegate the values of a property is to get them from a map, using the name of the
// property as the key of the map.
//This delegate lets us do really powerful things, because we can easily create an instance of an
// object from a dynamic map. If we are using immutable properties, the map can be immutable too.
// For mutable properties, the class will require a MutableMap as constructor parameter instead.

//The default constructor is getting a map, presumably filled with the values of the properties,
// and a dailyForecast. Thanks to the delegates, the values will be mapped
// to the corresponding properties based on the name of the key. If we want to make the mapping work
// perfectly, the names of the properties must be the same as the names of the columns in the database.
// We’ll see why later.
//But then, a second constructor is necessary. This is because we’ll be mapping classes
// from the domain back to the database.
// So instead of using a map, extracting the values from the properties will be more convenient.

class CityForecast(val map: MutableMap<String, Any?>, val dailyForecast: List<DayForecast>) {
    var _id: Long by map
    var city: String by map
    var country: String by map

    constructor(id: Long, city: String, country: String, dailyForecast: List<DayForecast>)
            : this(HashMap(), dailyForecast) {
        this._id = id
        this.city = city
        this.country = country
    }
}

class DayForecast(var map: MutableMap<String, Any?>) {
    var _id: Long by map
    var date: Long by map
    var description: String by map
    var high: Int by map
    var low: Int by map
    var iconUrl: String by map
    var cityId: Long by map

    constructor(date: Long, description: String, high: Int, low: Int, iconUrl: String, cityId: Long)
            : this(HashMap()) {
        this.date = date
        this.description = description
        this.high = high
        this.low = low
        this.iconUrl = iconUrl
        this.cityId = cityId
    }
}