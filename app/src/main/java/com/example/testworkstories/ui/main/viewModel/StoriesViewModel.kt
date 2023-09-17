package com.example.testworkstories.ui.main.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.testworkstories.R
import com.example.testworkstories.data.model.Story
import com.example.testworkstories.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StoriesViewModel : ViewModel() {
    private val webRepo = RetrofitClient()

    private val _stories : MutableStateFlow<List<Story>> = MutableStateFlow(
        emptyList()
    )

    val stories = _stories.asStateFlow()

    suspend fun requestStories(): String {
        val result = webRepo.retrofit.getStories()

       // Log.d("MyLog", "$result")

        return if (result.isSuccessful){
            _stories.value = result.body()?.detail!!.story
            R.string.loading_successful.toString()
        } else{
            R.string.loading_failed.toString()
        }

    }

    fun changeLikeOnItem(data: Story) {
        data.favorite = !data.favorite
        Log.d("MyLog", data.favorite.toString())
    }
}