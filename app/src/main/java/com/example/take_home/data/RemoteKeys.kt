package com.example.take_home.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
) : Parcelable
