package com.example.itunestest.util

import com.squareup.picasso.Picasso

object PicassoUtil{
    fun getPicasso() : Picasso{
        return Picasso.get()
    }
}