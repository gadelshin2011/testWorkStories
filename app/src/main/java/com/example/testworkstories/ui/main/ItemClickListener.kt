package com.example.testworkstories.ui.main

import com.example.testworkstories.data.model.Page
import com.example.testworkstories.data.model.Story

interface ItemClickListener {
    fun onItemClick(position: Int, data : Story)
}