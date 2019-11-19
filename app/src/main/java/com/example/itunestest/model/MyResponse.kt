package com.example.itunestest.model

import retrofit2.Response

//wrapper for Call<>
class MyResponse {
    var response: Response<Result>? = null
    var t : Throwable? = null
}