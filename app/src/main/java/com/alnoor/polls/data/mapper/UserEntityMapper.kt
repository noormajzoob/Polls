package com.alnoor.polls.data.mapper

import com.alnoor.polls.data.dto.UserDto
import com.alnoor.polls.domain.model.User
import com.alnoor.polls.domain.util.Mapper

class UserEntityMapper: Mapper<UserDto, User> {

    override fun mapFrom(data: User): UserDto {
        return UserDto(
            email = data.email,
            password = data.password
        )
    }

    override fun mapTo(data: UserDto): User {
        return User(
            id = data.id!!,
            name = data.name!!,
            email = data.email!!,
            password = data.password?: ""
        )
    }


}