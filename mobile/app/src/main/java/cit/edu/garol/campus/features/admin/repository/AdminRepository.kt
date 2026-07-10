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

    suspend fun getReportedItems():
            Response<List<AdminReportItem>> {

        return adminApi.getReportedItems()
    }

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
}