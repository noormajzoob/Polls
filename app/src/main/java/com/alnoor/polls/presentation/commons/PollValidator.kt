package com.alnoor.polls.presentation.commons

object PollValidator {

    fun validateTitle(title: String): Boolean{
        return title.length in 5..500
    }

    fun validateChoose(choose: String): Boolean{
        return choose.length in 5..205
    }

    fun validateChooseCount(count: Int): Boolean{
        return count in 2..5
    }

}