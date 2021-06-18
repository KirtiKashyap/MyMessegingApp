package com.example.mytest.model.response.conversation

import androidx.room.*

data class ConversationList(
        val data : List<ListData>)
@Entity(tableName = "conversation_list")
data class ListData(
        @PrimaryKey
        var id: Int,
        var started: String ? ="",
        var starter_id: Int ?=0,
        var accepted: Boolean ?=false,
        var last_activity: String ?="",
        var reported: String ?="",
        @TypeConverters
        var participants: List<Participants>,
        @TypeConverters
        var messages: List<Messages>
)
@Entity(tableName = "participants")
data class Participants(
        @PrimaryKey
        var id: Int ?=0,
        var name: String ?="",
        var first_name: String ?="",
        var photo: String ?,
        var date_of_birth: String ?="",
        @Embedded
        var university: University,
        @TypeConverters
        var sports: List<Sports>
)
@Entity(tableName = "messages")
data class Messages(
        @PrimaryKey
        var id: Int ?=0,
        var sender: Int ?=0,
        var message: String ?="",
        var read: Int ?=0,
        var sent: String ?=""
)
@Entity(tableName = "sports")
data class Sports(
        @PrimaryKey
        var id: Int ?=0,
        var name: String ?="",
        var ability: Int ?=0,
        var interest_level : Int ?=0
)
data class University(
        var name: String ?="",
        var degree_name: String ?="",
        var year_of_study: Int ?=0,
        @ColumnInfo(name="universityEmail")
        var email : String ?="",
        var verified : Boolean ?=false,
        var study_year : Int ?=0,
        var degree : Int ?=0,

)