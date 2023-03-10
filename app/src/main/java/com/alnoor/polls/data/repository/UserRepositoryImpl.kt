package com.alnoor.polls.data.repository

import com.alnoor.polls.data.dto.LoginRepoDto
import com.alnoor.polls.data.dto.UserDto
import com.alnoor.polls.data.mapper.LoginResponseMapper
import com.alnoor.polls.data.mapper.UserEntityMapper
import com.alnoor.polls.data.preference.UserPreferenceStore
import com.alnoor.polls.data.preference.UserPreferenceMapper
import com.alnoor.polls.data.service.UserService
import com.alnoor.polls.domain.model.LoginResponse
import com.alnoor.polls.domain.repesitory.UserRepository
import com.alnoor.polls.domain.util.InternetConnectivity
import com.alnoor.polls.domain.util.Resource
import com.alnoor.polls.domain.util.UserNotLoginException
import com.alnoor.polls.util.Constant
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userPreferenceStore: UserPreferenceStore,
    private val userEntityMapper: UserEntityMapper,
    private val userPreferenceMapper: UserPreferenceMapper,
    private val internetConnectivity: InternetConnectivity,
    private val loginResponseMapper: LoginResponseMapper
): UserRepository {

    override suspend fun login(email: String, password: String): Resource<LoginResponse> {
        try {
            if (!internetConnectivity.isConnect()){
                return Resource.Error("There is no internet connection")
            }

            val response = userService.login(
                UserDto(
                    email = email,
                    password = password
                )
            )

            return if (response.isSuccessful){
                val data = response.body()!!
                saveUserData(data)
                Resource.Success(loginResponseMapper.mapTo(data))
            }else{
                Resource.Error("User email or password is wrong")
            }
        }catch (e: Exception){
            e.printStackTrace()
            return Resource.Failure(e)
        }
    }

    override suspend fun signUp(email: String, password: String, userName: String): Resource<LoginResponse> {
        try {
            if (!internetConnectivity.isConnect()){
                return Resource.Error("There is no internet connection")
            }

            val response = userService.signUp(
                UserDto(
                    email = email,
                    password = password,
                    name = userName
                )
            )

            return if (response.isSuccessful){
                val user = userEntityMapper.mapTo(response.body()!!)
                val loginResponse = userService.login(userEntityMapper.mapFrom(user))
                if (loginResponse.isSuccessful){
                    val data = loginResponse.body()!!
                    saveUserData(data)
                    Resource.Success(loginResponseMapper.mapTo(data))
                }else{
                    Resource.Error("user email or password is incorrect!")
                }
            }else{
                Resource.Error("user email is already taken!")
            }
        }catch (e: Exception){
            e.printStackTrace()
            return Resource.Failure(e)
        }
    }

    private suspend fun saveUserData(loginRepoDto: LoginRepoDto){
        userPreferenceStore.putObject(
            loginRepoDto.user,
            key = Constant.USER_PREFS_KEY,
            mapper = userPreferenceMapper)
        userPreferenceStore.putPrimitive(key = Constant.TOKEN_PREF_KEY, data = loginRepoDto.token)
    }

    override suspend fun regenerateToken(){
        if (!userPreferenceStore.isLogged())
            throw UserNotLoginException()

        userPreferenceStore.getObject(Constant.USER_PREFS_KEY, userPreferenceMapper)?.let { user ->
            login(user.email!!, user.password!!)
        }
    }

}