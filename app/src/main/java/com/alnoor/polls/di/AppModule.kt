package com.alnoor.polls.di

import android.content.Context
import com.alnoor.polls.data.mapper.LoginResponseMapper
import com.alnoor.polls.data.mapper.PollSelectionMapper
import com.alnoor.polls.data.mapper.PollWrapperMapper
import com.alnoor.polls.data.mapper.UserEntityMapper
import com.alnoor.polls.data.preference.UserPreferenceStore
import com.alnoor.polls.data.preference.UserPreferenceMapper
import com.alnoor.polls.data.repository.PollRepositoryImpl
import com.alnoor.polls.data.repository.UserRepositoryImpl
import com.alnoor.polls.data.service.PollService
import com.alnoor.polls.data.service.UserService
import com.alnoor.polls.domain.repesitory.PollRepository
import com.alnoor.polls.domain.repesitory.UserRepository
import com.alnoor.polls.domain.util.InternetConnectivity
import com.alnoor.polls.util.Constant
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .callTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient, gson: Gson): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService{
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun providePollService(retrofit: Retrofit): PollService{
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): InternetConnectivity{
        return InternetConnectivity(context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userService: UserService,
        userPreferenceStore: UserPreferenceStore,
        userEntityMapper: UserEntityMapper,
        userPreferenceMapper: UserPreferenceMapper,
        loginResponseMapper: LoginResponseMapper,
        internetConnectivity: InternetConnectivity
    ): UserRepository{
        return UserRepositoryImpl(
            userService, userPreferenceStore, userEntityMapper, userPreferenceMapper, internetConnectivity, loginResponseMapper
        )
    }

    @Provides
    @Singleton
    fun providePollRepository(
        pollService: PollService,
        userRepository: UserRepository,
        userPreferenceStore: UserPreferenceStore,
        userPreferenceMapper: UserPreferenceMapper,
        pollSelectionMapper: PollSelectionMapper,
        pollWrapperMapper: PollWrapperMapper
    ): PollRepository{
        return PollRepositoryImpl(
            pollService,
            userRepository,
            userPreferenceStore,
            userPreferenceMapper,
            pollWrapperMapper,
            pollSelectionMapper
        )
    }

    @Provides
    @Singleton
    fun providePreferenceStore(@ApplicationContext context: Context): UserPreferenceStore{
        return UserPreferenceStore(context)
    }

}