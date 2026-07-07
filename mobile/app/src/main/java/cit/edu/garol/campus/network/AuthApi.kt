package cit.edu.garol.campus.network

import cit.edu.garol.campus.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/auth/register")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): Response<Any>
}