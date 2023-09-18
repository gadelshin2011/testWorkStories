package com.example.testworkstories.ui.main.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.testworkstories.data.dataStore.DataStoreManager
import com.example.testworkstories.data.model.Story
import com.example.testworkstories.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoriesViewModel(apl: Application) : AndroidViewModel(apl) {
    private val webRepo = RetrofitClient()
    private val dates = DataStoreManager(getApplication())
    // val listItem:Array<String> = arrayOf()

    private val _stories: MutableStateFlow<List<Story>> = MutableStateFlow(
        emptyList()
    )

    val stories = _stories.asStateFlow()

    suspend fun requestStories(): String {
        val result = webRepo.retrofit.getStories()

        return if (result.isSuccessful) {
            val itemFilterFavorite = dates.setFavorites(result.body()?.detail!!.story)
            _stories.value = itemFilterFavorite
            "Ok"
        } else {
            "Loading failed"
        }

    }

    fun changeLikeOnItem(data: Story) {
        data.favorite = !data.favorite
        Log.d("MyLog", data.favorite.toString())

        if (data.favorite) {
            viewModelScope.launch(Dispatchers.IO) {
                dates.saveItemStory(data.uniqueName)
                viewModelScope.launch {
                    Toast.makeText(getApplication(), "Добавлено в избранное" , Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                dates.deleteItem(data.uniqueName)
                viewModelScope.launch {
                    Toast.makeText(getApplication(), "Удалено из избранного" , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}

