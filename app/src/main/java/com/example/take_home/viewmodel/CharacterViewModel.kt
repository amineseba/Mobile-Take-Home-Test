package com.example.take_home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.take_home.data.AppDatabase
import com.example.take_home.data.Character
import com.example.take_home.paging.CharacterRemoteMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest


@OptIn(ExperimentalPagingApi::class, FlowPreview::class)
class CharacterViewModel(private val database: AppDatabase): ViewModel() {

    private val searchQuery = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val charactersFlow: Flow<PagingData<Character>> = searchQuery
        .debounce(300) //debounce to avoid rapid API calls
        .flatMapLatest { query ->
            Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false, prefetchDistance = 5),
                remoteMediator = CharacterRemoteMediator(query, database),
                pagingSourceFactory = { database.characterDao().pagingSource()}
            ).flow.cachedIn(viewModelScope)
        }

    fun updateSearchQuery(newQuery: String?) {
        searchQuery.value = newQuery
    }
}