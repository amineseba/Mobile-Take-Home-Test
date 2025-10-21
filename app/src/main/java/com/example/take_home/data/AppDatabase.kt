package com.example.take_home.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Character::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao() : CharacterDao
    abstract fun remoteKeysDao() : RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE : AppDatabase?= null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "character_database"
                ).build()
                INSTANCE = instance
                instance
            }
            }
        }
    }

