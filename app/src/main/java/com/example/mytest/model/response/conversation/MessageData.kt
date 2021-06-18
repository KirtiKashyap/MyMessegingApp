package com.example.mytest.model.response.conversation


data class MessageData(var data: List<Messages>,var meta : MetaMessage)

data class MetaMessage(var pagination : Pagination)

data class Pagination(
    var total: Int ?=0,
    var count : Int ?=0,
    var per_page : Int ?=0,
    var current_page : Int ?=0,
    var total_pages : Int ?=0,

)
