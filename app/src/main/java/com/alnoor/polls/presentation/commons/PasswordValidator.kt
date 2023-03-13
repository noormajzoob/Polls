package com.alnoor.polls.presentation.commons

object PasswordValidator {

    fun validate(pass: String): Boolean{
        return pass.length >= 6
    }

}