package com.example.mytest.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mytest.model.response.conversation.ListData
import com.example.mytest.model.response.conversation.Messages
import com.example.mytest.model.response.profile.ProfileData
import com.example.mytest.model.response.profile.UserProfile

@Dao
interface LocalDbDao {
    @Query("SELECT * FROM conversation_list")
    fun getAllConversationListFromDataBase() : LiveData <List<ListData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllConversation(albums: List<ListData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMessages(data: List<Messages>)

    @Query("SELECT * FROM messages")
    fun getAllMessages(): LiveData<List<Messages>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserProfile(data: ProfileData)

    @Query("SELECT * FROM user_profile")
    fun getUserProfile(): LiveData<ProfileData>

    @Query("SELECT * FROM conversation_list WHERE id = :id")
    fun getConversationById(id: String): LiveData<ListData>


}