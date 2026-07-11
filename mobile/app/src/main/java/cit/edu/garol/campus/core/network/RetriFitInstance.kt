package cit.edu.garol.campus.core.network

import cit.edu.garol.campus.features.admin.api.AdminApi
import cit.edu.garol.campus.features.authentication.network.AuthApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL =
        "http://10.0.2.2:8080/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    val adminApi: AdminApi by lazy {
        retrofit.create(AdminApi::class.java)
    }
}