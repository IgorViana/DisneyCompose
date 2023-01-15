package com.vianabrothers.android.myapplication.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vianabrothers.android.myapplication.network.DisneyService
import com.vianabrothers.android.myapplication.repository.DisneyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val TIMEOUT = 20L

    @Provides
    @Singleton
    fun provideRetrofitService(): Retrofit {

        val client = OkHttpClient.Builder()
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.disneyapi.dev")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideDisneyService(retrofit: Retrofit): DisneyService {
        return retrofit.create(DisneyService::class.java)
    }

    @Provides
    @Singleton
    fun provideDisneyRepository(disneyService: DisneyService): DisneyRepository {
        return DisneyRepository(disneyService)
    }
}