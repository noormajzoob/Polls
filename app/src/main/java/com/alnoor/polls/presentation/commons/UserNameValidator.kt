package com.alnoor.polls.presentation.commons

object UserNameValidator {

    fun validate(name: String): Boolean{
        return name.length >= 6
    }

}