package com.example.testworkstories.data.db.repository

import androidx.lifecycle.LiveData
import com.example.testworkstories.data.db.dao.StoryDao
import com.example.testworkstories.data.model.Story

class StoryRealization(private val storyDao: StoryDao):StoryRepository {
    override val allStory: LiveData<List<Story>>
        get() = storyDao.getAllStory()

    override suspend fun insertStory(storyModel: Story, onSuccess: () -> Unit) {
        storyDao.insert(storyModel)
        onSuccess()
    }

    override suspend fun deleteStory(storyModel: Story, onSuccess: () -> Unit) {
        storyDao.delete(storyModel)
        onSuccess()

    }

}