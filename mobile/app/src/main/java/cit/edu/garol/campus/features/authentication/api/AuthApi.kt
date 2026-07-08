package cit.edu.garol.campus.features.authentication.network

import cit.edu.garol.campus.features.authentication.model.LoginRequest
import cit.edu.garol.campus.features.authentication.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/auth/register")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): Response<Any>

    @POST("/api/auth/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<Any>
}


