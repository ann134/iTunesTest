package com.example.itunestest.api

import com.example.itunestest.model.Result
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ITunesApiService {

    @GET("search")
    fun search(@Query("term") term: String, @Query("entity") entity: String, @Query("attribute") attribute: String): Call<Result>

    @GET("lookup")
    fun lookup(@Query("id") id: Long, @Query("entity") entity: String): Call<Result>

    companion object Factory {
        fun create(): ITunesApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://itunes.apple.com/")
                .build()

            return retrofit.create(ITunesApiService::class.java)
        }
    }
}