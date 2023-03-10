package com.alnoor.polls.domain.util

sealed class Resource<T : Any> {
    class Success<T: Any>(val data: T) : Resource<T>()
    class Error<T: Any>(val message: String?) : Resource<T>()
    class Failure<T: Any>(val e: Throwable) : Resource<T>()
}
