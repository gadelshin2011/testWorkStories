package com.example.testworkstories.data.db.repository

import androidx.lifecycle.LiveData
import com.example.testworkstories.data.model.Story

interface StoryRepository {

    val allStory: LiveData<List<Story>>

    suspend fun insertStory(storyModel: Story, onSuccess: () -> Unit)
    suspend fun deleteStory(storyModel: Story, onSuccess: () -> Unit)
}