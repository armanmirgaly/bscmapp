package com.example.bscmapp_01;

import retrofit2.Call
import retrofit2.http.GET

public interface MyApi {

    @GET("exec")
    fun getComments(): Call<List<DataClass>>
}
