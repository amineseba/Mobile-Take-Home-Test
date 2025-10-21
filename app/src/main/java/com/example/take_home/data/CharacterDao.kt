package com.example.take_home.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertAll(characters: List<Character>)

    @Query("SELECT * FROM characters ORDER BY id ASC")
    fun pagingSource(): PagingSource<Int, Character>

    @Query("DELETE FROM characters")
    suspend fun clearAll()
}