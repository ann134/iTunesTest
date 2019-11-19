package com.example.itunestest.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itunestest.controllers.ResultRepository
import com.example.itunestest.model.MyResponse
import com.example.itunestest.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelAlbum : ViewModel() {

    private var repoModel: ResultRepository = ResultRepository()

    val data = MutableLiveData<MyResponse>()
    val isLoading = MutableLiveData<Boolean>()

    fun refresh(term : String) {
        isLoading.value = true
        repoModel.refreshAlbum(term, object : ResultRepository.OnDataReadyCallback<Result> {
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
        data.value = myResponse
        isLoading.value = false
    }

    fun setData(t: Throwable) {
        val myResponse = MyResponse()
        myResponse.t = t
        data.value = myResponse
        isLoading.value = false
    }
}