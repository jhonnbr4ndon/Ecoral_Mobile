package br.com.fiap.ecoral.entity

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("/usuario")
    fun createUser(@Body user: User): Call<Void>

    @POST("/usuario/login")
    fun loginUser(@Body user: User): Call<Void>

    @GET("/usuario/{id}")
    fun getUserById(@Path("id") userId: Long): Call<User>
}
