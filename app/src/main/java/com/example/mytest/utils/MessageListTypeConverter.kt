package com.example.mytest.utils

import androidx.room.TypeConverter
import com.example.mytest.model.response.conversation.ListData
import com.example.mytest.model.response.conversation.Messages
import com.example.mytest.model.response.conversation.Participants
import com.example.mytest.model.response.conversation.Sports
import com.example.mytest.model.response.profile.ProfileAvailability
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MessageListTypeConverter {
    @TypeConverter
    fun fromDeveloperList(countryLang: List<ListData?>?): String? {
        val type = object : TypeToken<List<ListData>>() {}.type
        return Gson().toJson(countryLang, type)
    }

    @TypeConverter
    fun toDeveloperList(countryLangString: String?): List<ListData>? {
        val type = object : TypeToken<List<ListData>>() {}.type
        return Gson().fromJson<List<ListData>>(countryLangString, type)
    }


    @TypeConverter
    fun fromParticipantsList(countryLang: List<Participants?>?): String? {
        val type = object : TypeToken<List<Participants>>() {}.type
        return Gson().toJson(countryLang, type)
    }

    @TypeConverter
    fun toParticipantsList(countryLangString: String?): List<Participants>? {
        val type = object : TypeToken<List<Participants>>() {}.type
        return Gson().fromJson<List<Participants>>(countryLangString, type)
    }



    @TypeConverter
    fun fromMessagesList(countryLang: List<Messages>?): String? {
        val type = object : TypeToken<List<Messages>>() {}.type
        return Gson().toJson(countryLang, type)
    }

    @TypeConverter
    fun toMessagesList(countryLangString: String?): List<Messages>? {
        val type = object : TypeToken<List<Messages>>() {}.type
        return Gson().fromJson<List<Messages>>(countryLangString, type)
    }



    @TypeConverter
    fun fromSportsList(countryLang: List<Sports>?): String? {
        val type = object : TypeToken<List<Sports>>() {}.type
        return Gson().toJson(countryLang, type)
    }

    @TypeConverter
    fun toSportsList(countryLangString: String): List<Sports>? {
        val type = object : TypeToken<List<Sports>>() {}.type
        return Gson().fromJson<List<Sports>>(countryLangString, type)
    }




    @TypeConverter
    fun fromAvailability(countryLang: List<ProfileAvailability>?): String? {
        val type = object : TypeToken<List<ProfileAvailability>>() {}.type
        return Gson().toJson(countryLang, type)
    }

    @TypeConverter
    fun toAvailability(countryLangString: String): List<ProfileAvailability>? {
        val type = object : TypeToken<List<ProfileAvailability>>() {}.type
        return Gson().fromJson<List<ProfileAvailability>>(countryLangString, type)
    }


}
