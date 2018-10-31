package com.elieterodrigues.weatherappkotlin.domain

interface Command<out T> {

    fun execute() : T
}