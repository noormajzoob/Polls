package com.alnoor.polls.presentation.commons

import com.google.gson.Gson
import javax.inject.Inject

private val gson = Gson()

fun <T: Any> T.toJson(): String = gson.toJson(this)

fun <T: Any> String.fromJson(type: Class<T>): T = gson.fromJson(this, type)