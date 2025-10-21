package com.example.take_home.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "characters")
data class Character(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val status: String,
    val species: String
) : Parcelable
