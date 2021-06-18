package com.example.mytest.di

import android.content.Context
import com.example.mytest.data.local.LocalDataBase
import com.example.mytest.data.local.LocalDbDao
import com.example.mytest.data.remote.EndPoints
import com.example.mytest.data.remote.RemoteData
import com.example.mytest.repo.AuthRepo
import com.example.mytest.repo.MessageRepo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object MyModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://armago-staging.herokuapp.com/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideAlbumService(retrofit: Retrofit): EndPoints = retrofit.create(EndPoints::class.java)

    @Singleton
    @Provides
    fun provideAlbumRemoteDataSource(characterService: EndPoints) = RemoteData(characterService)

     @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = LocalDataBase.getDatabase(appContext)
    @Singleton
    @Provides
    fun provideAlbumDao(db: LocalDataBase) = db.localDbDao()
    @Singleton
    @Provides
    fun provideMessageRepository(remoteDataSource: RemoteData,
                          localDataSource: LocalDbDao
    ) =
        MessageRepo(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideAuthRepository(remoteDataSource: RemoteData,localDataSource: LocalDbDao) =
        AuthRepo(remoteDataSource,localDataSource)
}