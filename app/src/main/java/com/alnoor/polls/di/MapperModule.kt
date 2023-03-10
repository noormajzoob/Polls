package com.alnoor.polls.di

import com.alnoor.polls.data.mapper.*
import com.alnoor.polls.data.preference.UserPreferenceMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideGson(): Gson{
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideUserMapper(): UserEntityMapper{
        return UserEntityMapper()
    }

    @Provides
    @Singleton
    fun provideLoginMapper(userEntityMapper: UserEntityMapper): LoginResponseMapper{
        return LoginResponseMapper(userEntityMapper)
    }

    @Provides
    @Singleton
    fun provideUserPrefMapper(gson: Gson): UserPreferenceMapper{
        return UserPreferenceMapper(gson)
    }

    @Provides
    @Singleton
    fun providePollWrapperMapper(
        pollEntityMapper: PollEntityMapper,
        chooseEntityMapper: PollChooseEntityMapper
    ): PollWrapperMapper{
        return PollWrapperMapper(pollEntityMapper, chooseEntityMapper)
    }

    @Provides
    @Singleton
    fun providePollChooseMapper(): PollChooseEntityMapper{
        return PollChooseEntityMapper()
    }

    @Provides
    @Singleton
    fun providePollSelectionMapper(
        userEntityMapper: UserEntityMapper,
        chooseEntityMapper: PollChooseEntityMapper
    ): PollSelectionMapper{
        return PollSelectionMapper(userEntityMapper, chooseEntityMapper)
    }

}