package com.kiennv31.smartmovie.di

import com.kiennv31.smartmovie.core.network.ApiKeyInterceptor
import com.kiennv31.smartmovie.data.remote.api.ApiConstants
import com.kiennv31.smartmovie.data.remote.api.CastApi
import com.kiennv31.smartmovie.data.remote.api.GenreApi
import com.kiennv31.smartmovie.data.remote.api.MovieApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGenreApi(retrofit: Retrofit): GenreApi {
        return retrofit.create(GenreApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCastApi(retrofit: Retrofit): CastApi {
        return retrofit.create(CastApi::class.java)
    }
}
