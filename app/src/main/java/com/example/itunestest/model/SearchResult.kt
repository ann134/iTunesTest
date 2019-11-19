package com.example.itunestest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MusicItem(
    val artistName: String,
    val collectionName: String,
    val artworkUrl100: String,
    val collectionId: Long,
    val trackName: String,
    val trackNumber: Int
) : Parcelable

data class Result(
    val resultCount: Int,
    val results: List<MusicItem>
)
