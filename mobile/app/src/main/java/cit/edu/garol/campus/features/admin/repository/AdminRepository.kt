package cit.edu.garol.campus.features.admin.repository

import cit.edu.garol.campus.features.admin.api.AdminApi
import cit.edu.garol.campus.features.admin.model.AdminLoginRequest
import cit.edu.garol.campus.features.admin.model.AdminLoginResponse
import cit.edu.garol.campus.features.admin.model.AdminReportItem
import cit.edu.garol.campus.features.admin.model.AdminStatusRequest
import retrofit2.Response

class AdminRepository(
    private val adminApi: AdminApi
) {

    /* ADMIN LOGIN */

    suspend fun loginAdmin(
        adminId: String,
        password: String
    ): Response<AdminLoginResponse> {

        return adminApi.loginAdmin(
            AdminLoginRequest(
                emailOrId = adminId,
                password = password
            )
        )
    }

    /* GET REPORTED ITEMS */

    suspend fun getReportedItems(): Response<List<AdminReportItem>> {
        return adminApi.getReportedItems()
    }

    /*
     * APPROVE REPORT

     * Under Review -> Unclaimed
     */

    suspend fun approveReport(
        reportId: String
    ): Response<AdminReportItem> {

        return adminApi.approveReport(reportId)
    }

    /*
     * ==========================================================
     * REJECT REPORT
     *
     * Under Review -> Rejected
     * ==========================================================
     */

    suspend fun rejectReport(
        reportId: String
    ): Response<AdminReportItem> {

        return adminApi.rejectReport(reportId)
    }

    /*
     * ==========================================================
     * UPDATE PUBLIC STATUS
     *
     * Unclaimed
     * Pending Claim
     * Claimed
     * ==========================================================
     */

    suspend fun updateReportStatus(
        reportId: String,
        status: String
    ): Response<AdminReportItem> {

        return adminApi.updateReportStatus(
            reportId = reportId,
            request = AdminStatusRequest(
                status = status
            )
        )
    }

    suspend fun getPublicReports():
            Response<List<AdminReportItem>> {

        return adminApi.getPublicReports()

    }
}