package br.com.fiap.ecoral.entity

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // 192.168.0.55
    // 179.88.5.152
    private const val BASE_URL = "http://192.168.0.55:8080/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}