package com.example.itunestest.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itunestest.controllers.ResultRepository
import com.example.itunestest.model.MyResponse
import com.example.itunestest.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelSong : ViewModel() {

    private var repoModel: ResultRepository = ResultRepository()

    val dataSongs = MutableLiveData<MyResponse>()
    val isLoadingSongs = MutableLiveData<Boolean>()

    fun refresh(id: Long) {
        isLoadingSongs.value = true
        Log.d("refresh", id.toString())
        repoModel.refreshSong(id, object : ResultRepository.OnDataReadyCallback<Result> {
            override fun onDataReady(call: Call<Result>) {
                call.enqueue(object : Callback<Result> {
                    override fun onResponse(call: Call<Result>, response: Response<Result>) {
                        setData(response)
                    }

                    override fun onFailure(call: Call<Result>, t: Throwable) {
                        setData(t)
                    }
                })
            }
        })
    }

    fun setData(response: Response<Result>) {
        val myResponse = MyResponse()
        myResponse.response = response
        dataSongs.value = myResponse
        isLoadingSongs.value = false
    }

    fun setData(t: Throwable) {
        val myResponse = MyResponse()
        myResponse.t = t
        dataSongs.value = myResponse
        isLoadingSongs.value = false
    }
}