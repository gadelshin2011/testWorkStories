package com.example.testworkstories.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.testworkstories.data.model.Story


@Dao
interface StoryDao {
    @Insert
    suspend fun insert(story: Story)

    @Delete
    suspend fun delete(story: Story)

    @Query("SELECT * from story_table")
    fun getAllStory(): LiveData<List<Story>>
}