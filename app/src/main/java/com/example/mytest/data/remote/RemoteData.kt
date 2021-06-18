package com.example.mytest.data.remote

import com.example.mytest.model.request.SendMessage
import com.example.mytest.model.request.TokenRequest
import javax.inject.Inject

class RemoteData @Inject constructor(
private val apiService: EndPoints
): RemoteBaseData(){

    suspend fun getMessages(token: String, id: Int) = getResult { apiService.getMessage(token,id) }
    suspend fun getConversationList(token: String)=getResult { apiService.getConversationList(token) }

    suspend fun getMessage(token: String,id:Int)=getResult { apiService.getMessage(token,id) }

    suspend fun sendMessage(token: String, id: Int, sendMessage: SendMessage)=getResult { apiService.sendMessage(token,id,sendMessage) }

    suspend fun getToken(request: TokenRequest) = getResult { apiService.getToken(request) }

    suspend fun getUserProfile(token: String) = getResult { apiService.getUserProfile(token) }
}