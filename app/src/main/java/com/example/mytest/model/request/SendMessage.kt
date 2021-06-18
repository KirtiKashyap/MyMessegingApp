package com.example.mytest.model.request

data class SendMessage(
    var message : String,
    var content : String,
    val sender : Int,
    val sent : String
)