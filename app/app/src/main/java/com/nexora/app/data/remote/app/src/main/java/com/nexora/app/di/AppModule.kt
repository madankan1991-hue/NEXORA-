package com.nexora.app.di

import android.content.Context
import androidx.room.Room
import com.nexora.app.data.local.AnalysisDao
import com.nexora.app.data.local.NexoraDatabase
import com.nexora.app.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .connectTimeout(30, TimeUnit.SECONDS).build()

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/").client(client)
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NexoraDatabase =
        Room.databaseBuilder(context, NexoraDatabase::class.java, "nexora_db").fallbackToDestructiveMigration().build()

    @Provides @Singleton
    fun provideDao(db: NexoraDatabase): AnalysisDao = db.analysisDao()
}
