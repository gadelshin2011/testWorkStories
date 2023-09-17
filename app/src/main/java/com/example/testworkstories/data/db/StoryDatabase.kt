package com.example.testworkstories.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testworkstories.data.db.dao.StoryDao
import com.example.testworkstories.data.model.Story

@Database(entities = [Story::class], version = 1)

abstract class StoryDatabase:RoomDatabase() {
    abstract fun getStoryDao():StoryDao
    
    
    companion object{
        private var database: StoryDatabase?= null

        @Synchronized
        fun getInstance(context: Context): StoryDatabase{

            return if (database == null){
                database = Room.databaseBuilder(context, StoryDatabase::class.java, "story_db").build()
                database as StoryDatabase
            } else{
                database as StoryDatabase
            }

        }
    }
}