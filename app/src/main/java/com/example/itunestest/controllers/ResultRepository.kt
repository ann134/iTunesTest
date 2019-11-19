package com.example.itunestest.controllers

import com.example.itunestest.api.ITunesApiService
import com.example.itunestest.model.Result
import retrofit2.Call

class ResultRepository {

    fun refreshAlbum(term: String, onDataReadyCallback: OnDataReadyCallback<Result>) {
        val call = ITunesApiService.create().search(term, "album","albumTerm" )
        onDataReadyCallback.onDataReady(call)
    }

    fun refreshSong(id: Long, onDataReadyCallback: OnDataReadyCallback<Result>) {
        val call = ITunesApiService.create().lookup(id, "song")
        onDataReadyCallback.onDataReady(call)
    }

    interface OnDataReadyCallback<T> {
        fun onDataReady(call: Call<T>)
    }
}
