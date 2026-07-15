package cit.edu.garol.campus.features.claimrequest.repository

import cit.edu.garol.campus.features.claimrequest.api.ClaimRequestApi
import cit.edu.garol.campus.features.claimrequest.model.ClaimRequest
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
}