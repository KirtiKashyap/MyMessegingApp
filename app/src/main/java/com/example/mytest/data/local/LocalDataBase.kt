package com.example.mytest.data.local

import android.content.Context
import androidx.room.*
import com.example.mytest.model.response.conversation.ListData
import com.example.mytest.model.response.conversation.Messages
import com.example.mytest.model.response.profile.ProfileData
import com.example.mytest.utils.MessageListTypeConverter

@Database(entities = [ListData::class, Messages::class, ProfileData::class], version = 1, exportSchema = false)
@TypeConverters(MessageListTypeConverter::class)
abstract class LocalDataBase : RoomDatabase(){

    abstract fun localDbDao(): LocalDbDao

    companion object {
        @Volatile
        private var instance: LocalDataBase? = null

        fun getDatabase(context: Context): LocalDataBase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, LocalDataBase::class.java, "messageListDb")
                .fallbackToDestructiveMigration()
                .build()
    }

}