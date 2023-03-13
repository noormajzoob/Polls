package com.alnoor.polls.presentation.commons

object EmailValidator {

    private const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    fun validate(email: String): Boolean {
        return EMAIL_REGEX.toRegex().matches(email);
    }

}