package com.example.testworkstories.data.dataStore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.testworkstories.data.model.Story
import kotlinx.coroutines.flow.first

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preference")

class DataStoreManager(context: Context) {

    private val dataStore = context.dataStore

    suspend fun saveItemStory(item: String) {
        dataStore.edit { preferences ->
            val items = preferences[KEY_ITEMS]?.toMutableSet() ?: mutableSetOf()
            items.add(item)
            preferences[KEY_ITEMS] = items
            Log.d("MyLogg", preferences[KEY_ITEMS].toString())
        }
    }

    private suspend fun getList(): List<String> {
        val preferences = dataStore.data.first()
        return preferences[KEY_ITEMS]?.toList() ?: emptyList()
    }

    suspend fun setFavorites(list: List<Story>): List<Story> {
        val listDataStory = getList()

        for (item in list) {
            if (listDataStory.contains(item.uniqueName)) {
                item.favorite = true
            }
        }

        return list
    }

    suspend fun deleteItem(item: String) {
        dataStore.edit { preferences ->
            val items = preferences[KEY_ITEMS]?.toMutableSet() ?: mutableSetOf()
            items.remove(item)
            preferences[KEY_ITEMS] = items
            Log.d("MyLogg", preferences[KEY_ITEMS].toString())
        }
    }


    companion object {
        private val KEY_ITEMS = stringSetPreferencesKey("key_items")
    }


}