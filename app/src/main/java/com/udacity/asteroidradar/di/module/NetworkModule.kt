package com.udacity.asteroidradar.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.network.api.ApiService
import com.udacity.asteroidradar.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Client for modification of connection time-out.
    // Furthermore, since more than one network API has api_key as a query parameter,
    // create an API interceptor and chain it with your network requests to avoid
    // repetitively passing the API key as a parameter.
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val url = chain
                .request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.NASA_API_KEY)
                .build()
            chain.proceed(chain.request().newBuilder().url(url).build())
        }
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    //Provide the Moshi converter used by Retrofit (KotlinJsonAdapterFactory = from JSON object to Kotlin object)
    //This is used for the "Image of the day"
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    @Provides
    @Singleton
    @Named("ServiceImage")
    fun provideRetrofitImage(okHttpClient: OkHttpClient, moshi: Moshi): ApiService =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    @Named("ServiceAsteroid")
    fun provideRetrofitAsteroid(okHttpClient: OkHttpClient): ApiService = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .build()
        .create(ApiService::class.java)

}
