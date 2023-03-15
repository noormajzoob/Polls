package com.alnoor.polls.presentation.commons

import com.google.gson.Gson
import javax.inject.Inject


fun <T> T.toJson(): String = Gson().toJson(this)

fun <T> String.fromJson(type: Class<T>): T = Gson().fromJson(this, type)