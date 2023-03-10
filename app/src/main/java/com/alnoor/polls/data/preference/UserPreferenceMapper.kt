package com.alnoor.polls.data.preference

import com.alnoor.polls.data.dto.UserDto
import com.alnoor.polls.domain.util.Mapper
import com.google.gson.Gson

class UserPreferenceMapper(private val gson: Gson): Mapper<UserDto, String>{

    override fun mapFrom(data: String): UserDto {
        return gson.fromJson(data, UserDto::class.java)
    }

    override fun mapTo(data: UserDto): String {
        return gson.toJson(data)
    }

}