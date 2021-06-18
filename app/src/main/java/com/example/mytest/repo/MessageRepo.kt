package com.example.mytest.repo

import com.example.mytest.data.local.LocalDbDao
import com.example.mytest.data.remote.RemoteData
import com.example.mytest.model.request.SendMessage
import com.example.mytest.utils.performOperation
import javax.inject.Inject

class MessageRepo @Inject constructor(
    private val remoteDataSource: RemoteData,
    private val localDataSource: LocalDbDao){

    suspend fun sendMessage(token: String, id: Int, sendMessage: SendMessage) =
        remoteDataSource.sendMessage(token,id,sendMessage)

    fun getMessageList(id: Int,token: String)= performOperation(
        databaseQuery = { localDataSource.getAllMessages() },
        networkCall = { remoteDataSource.getMessages(token,id)},
        saveCallResult = {localDataSource.insertAllMessages(it.data)}
    )

    fun getAllConversation(token: String) = performOperation(
        databaseQuery = { localDataSource.getAllConversationListFromDataBase() },
        networkCall = { remoteDataSource.getConversationList(token)},
        saveCallResult = {localDataSource.insertAllConversation(it.data)}
    )


}
