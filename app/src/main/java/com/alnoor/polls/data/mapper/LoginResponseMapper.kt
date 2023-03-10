package com.alnoor.polls.data.mapper

import com.alnoor.polls.data.dto.LoginRepoDto
import com.alnoor.polls.domain.model.LoginResponse
import com.alnoor.polls.domain.util.Mapper

class LoginResponseMapper(private val userEntityMapper: UserEntityMapper): Mapper<LoginRepoDto, LoginResponse> {

    override fun mapFrom(data: LoginResponse): LoginRepoDto {
        return LoginRepoDto(
            user = userEntityMapper.mapFrom(data.user),
            token = data.token
        )
    }

    override fun mapTo(data: LoginRepoDto): LoginResponse {
        return LoginResponse(
            user = userEntityMapper.mapTo(data.user),
            token = data.token
        )
    }

}