package com.alnoor.polls.domain.util

interface Mapper<T, P> {

    fun mapFrom(data: P): T
    fun mapTo(data: T): P

}