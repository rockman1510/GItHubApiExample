package com.example.githubapiexample.di.module

import android.content.Context
import com.example.githubapiexample.BuildConfig
import com.example.githubapiexample.Constants
import com.example.githubapiexample.api.ApiService
import com.example.githubapiexample.roomdatabase.AppDataBase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder().readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(logging)
        }
        return client.build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl).client(okHttpClient).build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideAppDataBase(context: Context): AppDataBase = AppDataBase.getRoomDatabase(context)
}