package com.example.testworkstories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testworkstories.ui.main.StoriesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, StoriesFragment.newInstance())
                .commitNow()
        }
    }
}