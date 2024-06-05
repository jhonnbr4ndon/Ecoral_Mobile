package br.com.fiap.ecoral.entity

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("/usuario")
    fun createUser(@Body user: User): Call<Void>

    @POST("/usuario/login")
    fun loginUser(@Body credentials: Credentials): Call<User>

    @PUT("/usuario/{id}")
    fun atualizarNomeUsuario(@Path("id") id: Long, @Body novoNome: UsuarioDTO): Call<Void>

    @DELETE("/usuario/{id}")
    fun deletarUsuario(@Path("id") id: Long): Call<Void>

}

