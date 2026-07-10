package cit.edu.garol.campus.features.admin.api

import cit.edu.garol.campus.features.admin.model.AdminLoginRequest
import cit.edu.garol.campus.features.admin.model.AdminLoginResponse
import cit.edu.garol.campus.features.admin.model.AdminReportItem
import cit.edu.garol.campus.features.admin.model.AdminStatusRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AdminApiService {

    @POST("api/admin/login")
    suspend fun loginAdmin(
        @Body request: AdminLoginRequest
    ): Response<AdminLoginResponse>

    @GET("api/admin/reported-items")
    suspend fun getReportedItems(): Response<List<AdminReportItem>>

    @PUT("api/admin/reports/{id}/status")
    suspend fun updateReportStatus(
        @Path("id") reportId: Long,
        @Body request: AdminStatusRequest
    ): Response<AdminReportItem>
}