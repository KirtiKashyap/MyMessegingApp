package com.example.mytest.data.remote

import com.example.mytest.model.request.SendMessage
import com.example.mytest.model.request.TokenRequest
import com.example.mytest.model.response.conversation.ConversationList
import com.example.mytest.model.response.conversation.MessageData
import com.example.mytest.model.response.profile.UserProfile
import com.example.mytest.model.response.token.GetToken
import retrofit2.Response
import retrofit2.http.*

interface EndPoints {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("user/token")
    suspend fun getToken(@Body tokenRequest: TokenRequest): Response<GetToken>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("message/conversation")
    suspend fun getConversationList(
        @Header("AUTHORIZATION") value: String ): Response<ConversationList>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("user/profile")
    suspend fun getUserProfile(
        @Header("AUTHORIZATION") value: String ): Response<UserProfile>


    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("message/conversation/{conversation_id}")
    suspend fun getMessage(
        @Header("AUTHORIZATION") value: String,
        @Path("conversation_id")id: Int): Response<MessageData>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @HTTP(method = "POST",path = "message/conversation/{conversation_id}", hasBody = true)
    suspend fun sendMessage(
        @Header("AUTHORIZATION") value: String,
        @Path("conversation_id") id: Int,
        @Body sendMessage: SendMessage
    ): Response<Unit>



}