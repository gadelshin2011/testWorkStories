package com.example.testworkstories.ui.main.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testworkstories.data.db.StoryDatabase
import com.example.testworkstories.data.db.repository.StoryRealization
import com.example.testworkstories.data.db.repository.StoryRepository
import com.example.testworkstories.data.model.Story
import com.example.testworkstories.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoriesViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var REPOSITORY:StoryRepository
    private val context = application
    private val webRepo = RetrofitClient()

    private val _stories: MutableStateFlow<List<Story>> = MutableStateFlow(
        emptyList()
    )

    val stories = _stories.asStateFlow()

    suspend fun requestStories(): String {
        val result = webRepo.retrofit.getStories()

        return if (result.isSuccessful) {
            _stories.value = result.body()?.detail!!.story
            "Ok"
        } else {
            "Loading failed"
        }

    }

    fun initDatabase() {
            val daoStory = StoryDatabase.getInstance(context).getStoryDao()
            REPOSITORY = StoryRealization(daoStory)

    }

    fun getAllStory():LiveData<List<Story>>{
        return REPOSITORY.allStory
    }

    fun delete(storyModel:Story,onSuccess:()->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.deleteStory(storyModel){
                onSuccess()
            }
        }
    }
    private fun insert(storyModel:Story, onSuccess:()->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.insertStory(storyModel){
                onSuccess()
            }
        }
    }


    fun changeLikeOnItem(data: Story) {
        data.favorite = !data.favorite
        Log.d("MyLog", data.favorite.toString())
        if (data.favorite) {
           insert(data) {}
        }




    }

}