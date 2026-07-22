package cit.edu.garol.campus.features.report.repository

import cit.edu.garol.campus.core.network.RetrofitInstance
import cit.edu.garol.campus.features.admin.model.AdminReportItem
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ReportRepository {

    /*
     * ==========================================================
     * PUBLIC REPORTS
     * ==========================================================
     */

    suspend fun getPublicReports(): List<AdminReportItem> {
        return RetrofitInstance
            .reportApi
            .getPublicReports()
    }

    /*
     * ==========================================================
     * USER REPORTS
     * ==========================================================
     */

    suspend fun getMyReports(
        userId: String
    ): List<AdminReportItem> {

        return RetrofitInstance
            .reportApi
            .getMyReports(userId)

    }

    /*
     * ==========================================================
     * CREATE REPORT
     * ==========================================================
     */

    suspend fun createReport(
        userId: RequestBody,
        reportType: RequestBody,
        itemName: RequestBody,
        category: RequestBody,
        description: RequestBody,
        location: RequestBody,
        image: MultipartBody.Part?

    ): AdminReportItem {
        return RetrofitInstance
            .reportApi
            .createReport(
                userId = userId,
                reportType = reportType,
                itemName = itemName,
                category = category,
                description = description,
                location = location,
                image = image

            )
    }
}