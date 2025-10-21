package com.example.take_home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.take_home.data.AppDatabase

class CharactereViewModelFactory(private val database: AppDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterViewModel(database = database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}