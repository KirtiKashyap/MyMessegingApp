package com.example.mytest.repo

import com.example.mytest.data.local.LocalDbDao
import com.example.mytest.data.remote.RemoteData
import com.example.mytest.model.request.TokenRequest
import com.example.mytest.utils.performOperation
import javax.inject.Inject

class AuthRepo@Inject constructor(
    private val remoteDataSource: RemoteData,
    private val localDataSource: LocalDbDao
){

    suspend fun getAuthorization(request: TokenRequest) =
        remoteDataSource.getToken(request)

    fun getUserProfile(token: String) = performOperation(
        databaseQuery = { localDataSource.getUserProfile() },
        networkCall = { remoteDataSource.getUserProfile(token)},
        saveCallResult = {localDataSource.insertUserProfile(it.data)}
    )
}