package cit.edu.garol.campus.features.claimrequest.repository

import cit.edu.garol.campus.features.claimrequest.api.ClaimRequestApi
import cit.edu.garol.campus.features.claimrequest.model.ClaimRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class ClaimRequestRepository(
    private val claimRequestApi: ClaimRequestApi
) {

    /*
     * ==========================================================
     * ADMIN - GET ALL CLAIM REQUESTS
     * ==========================================================
     */

    suspend fun getAllClaimRequests():
            Response<List<ClaimRequest>> {

        return claimRequestApi.getAllClaimRequests()
    }

    /*
     * ==========================================================
     * USER - GET OWN CLAIM REQUESTS
     * ==========================================================
     */

    suspend fun getUserClaimRequests(
        claimantId: String
    ): Response<List<ClaimRequest>> {

        return claimRequestApi.getUserClaimRequests(
            claimantId
        )
    }

    /*
     * ==========================================================
     * ADMIN - APPROVE CLAIM REQUEST
     * ==========================================================
     */

    suspend fun approveClaimRequest(
        claimRequestId: Int
    ): Response<String> {

        return claimRequestApi.approveClaimRequest(
            claimRequestId
        )
    }

    /*
     * ==========================================================
     * ADMIN - REJECT CLAIM REQUEST
     * ==========================================================
     */

    suspend fun rejectClaimRequest(
        claimRequestId: Int
    ): Response<String> {

        return claimRequestApi.rejectClaimRequest(
            claimRequestId
        )
    }

    /*
     * ==========================================================
     * ADMIN - UPDATE CLAIM STATUS
     * ==========================================================
     */

    suspend fun updateClaimStatus(
        claimRequestId: Int,
        status: String
    ): Response<String> {

        return claimRequestApi.updateClaimStatus(
            claimRequestId = claimRequestId,
            status = status
        )
    }

    suspend fun submitClaimRequest(

        itemReportId: RequestBody,
        claimantId: RequestBody,
        claimantName: RequestBody,
        claimantEmail: RequestBody,
        claimantPhone: RequestBody,
        itemDescription: RequestBody,
        additionalInformation: RequestBody,
        proofImage: MultipartBody.Part?
    ): Response<ClaimRequest> {
        return claimRequestApi.submitClaimRequest(
            itemReportId = itemReportId,
            claimantId = claimantId,
            claimantName = claimantName,
            claimantEmail = claimantEmail,
            claimantPhone = claimantPhone,
            itemDescription = itemDescription,
            additionalInformation = additionalInformation,
            proofImage = proofImage

        )

    }
}