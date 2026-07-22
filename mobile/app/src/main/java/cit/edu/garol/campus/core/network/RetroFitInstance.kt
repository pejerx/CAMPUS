package cit.edu.garol.campus.core.network

import cit.edu.garol.campus.core.ApiConfig
import cit.edu.garol.campus.features.admin.api.AdminApi
import cit.edu.garol.campus.features.authentication.network.AuthApi
import cit.edu.garol.campus.features.claimrequest.api.ClaimRequestApi
import cit.edu.garol.campus.features.report.api.ReportApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    /*
     * ==========================================================
     * AUTH API
     * ==========================================================
     */

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    /*
     * ==========================================================
     * ADMIN API
     * ==========================================================
     */

    val adminApi: AdminApi by lazy {
        retrofit.create(AdminApi::class.java)
    }

    /*
     * ==========================================================
     * CLAIM REQUEST API
     * ==========================================================
     */

    val claimRequestApi: ClaimRequestApi by lazy {
        retrofit.create(ClaimRequestApi::class.java)
    }

    /*
     * ==========================================================
     * REPORT API
     * ==========================================================
     */

        val reportApi: ReportApi by lazy {
            retrofit.create(ReportApi::class.java)
        }
}