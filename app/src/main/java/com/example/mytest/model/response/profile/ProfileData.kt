package com.example.mytest.model.response.profile

import androidx.room.*
import com.example.mytest.model.response.conversation.Sports
import com.example.mytest.model.response.conversation.University

@Entity(tableName = "user_profile")
data class ProfileData(
    @PrimaryKey
    var id : Int ?=0,
    var first_name : String ?="",
    var last_name: String ?="",
    var date_of_birth : String ?="",
    var email : String ?="",
    var photo : String? ="",
    var phone_number : String? ="",
    var gender : Int ?=0,
    var university_id : Int ?=0,
    @Embedded
var university : University,
    @Embedded
var location : ProfileLocation,
    @TypeConverters
var sports : List<Sports>,
    @TypeConverters
var availability : List<ProfileAvailability>
)

data class ProfileLocation(
    @PrimaryKey
    var method : Int ?=0,
    var longitude : Double ?,
    var latitude : Double ?,
    var address_line_1 : String ?="",
    var address_line_2 : String ?="",
    var address_town : String ?="",
    var address_county : String ?="",
    var address_post_code : String ?=""
)


data class ProfileAvailability(
    @PrimaryKey
    var id : Int ?=0,
    var day : Int ?=0,
    var am : Boolean ?=false,
    var pm : Boolean ?=false,
    var evening : Boolean ?=false
)
