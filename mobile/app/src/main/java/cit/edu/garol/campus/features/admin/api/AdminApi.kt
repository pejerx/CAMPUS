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

interface AdminApi {

    /*
     * ==========================================================
     * ADMIN LOGIN
     * ==========================================================
     */

    @POST("api/admin/login")
    suspend fun loginAdmin(
        @Body request: AdminLoginRequest
    ): Response<AdminLoginResponse>

    /*
     * ==========================================================
     * REPORTS
     * ==========================================================
     */

    @GET("api/admin/reported-items")
    suspend fun getReportedItems(): Response<List<AdminReportItem>>

    /*
     * ==========================================================
     * APPROVE REPORT
     *
     * Under Review -> Unclaimed
     * ==========================================================
     */

    @PUT("api/admin/reports/{id}/approve")
    suspend fun approveReport(
        @Path("id") reportId: String
    ): Response<AdminReportItem>

    /*
     * ==========================================================
     * REJECT REPORT
     *
     * Under Review -> Rejected
     * ==========================================================
     */

    @PUT("api/admin/reports/{id}/reject")
    suspend fun rejectReport(
        @Path("id") reportId: String
    ): Response<AdminReportItem>

    /*
     * ==========================================================
     * UPDATE PUBLIC STATUS
     *
     * Unclaimed
     * Pending Claim
     * Claimed
     * ==========================================================
     */

    @PUT("api/admin/reports/{id}/status")
    suspend fun updateReportStatus(
        @Path("id") reportId: String,
        @Body request: AdminStatusRequest
    ): Response<AdminReportItem>

    @GET("api/reports/public")
    suspend fun getPublicReports():
            Response<List<AdminReportItem>>
}