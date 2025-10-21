package com.example.take_home.paging

import androidx.core.net.toUri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.take_home.data.AppDatabase
import com.example.take_home.data.Character
import com.example.take_home.data.RemoteKeys
import com.example.take_home.network.ApiClient
import okio.IOException
import retrofit2.HttpException


@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val query: String?,
    private val database: AppDatabase
) : RemoteMediator<Int, Character>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        try {

            val response = ApiClient.apiService.getCharacters(page,query)

            val characters = response.results

            val endOfPagination = false


            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.characterDao().clearAll()
                }

                val previousKey = if (page > 1) page - 1 else null
                val nextKey = if (!endOfPaginationReached) page + 1 else null
                val keys = characters.map {
                    RemoteKeys(
                        id = it.id, prevKey = previousKey,
                        nextKey = nextKey
                    )
                }
                database.remoteKeysDao().insertAll(keys)
                database.characterDao().insertAll(characters)

            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

        private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Character>): RemoteKeys? {
            return state.pages.firstOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()
                ?.let { character -> database.remoteKeysDao().remoteKeys(id = character.id) }
        }


        private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Character>): RemoteKeys? {
            return state.pages.lastOrNull(){it.data.isNotEmpty()}?.data?.lastOrNull()?.let { character -> database.remoteKeysDao().remoteKeys(id = character.id) }
        }

        private fun extractPageNumber(url: String?): Int? {
            return url?.let { it.toUri().getQueryParameter("page")?.toIntOrNull() }
        }


}