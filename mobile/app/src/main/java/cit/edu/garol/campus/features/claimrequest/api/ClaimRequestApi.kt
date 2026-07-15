package cit.edu.garol.campus.features.claimrequest.api

import cit.edu.garol.campus.features.claimrequest.model.ClaimRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ClaimRequestApi {

    /*
     * ==========================================================
     * ADMIN - GET ALL CLAIM REQUESTS
     * ==========================================================
     */

    @GET("api/claim-requests")
    suspend fun getAllClaimRequests():
            Response<List<ClaimRequest>>

    /*
     * ==========================================================
     * USER - GET OWN CLAIM REQUESTS
     * ==========================================================
     */

    @GET("api/claim-requests/user/{claimantId}")
    suspend fun getUserClaimRequests(
        @Path("claimantId")
        claimantId: String
    ): Response<List<ClaimRequest>>

    /*
     * ==========================================================
     * ADMIN - APPROVE CLAIM REQUEST
     * ==========================================================
     */

    @PUT("api/claim-requests/{id}/approve")
    suspend fun approveClaimRequest(
        @Path("id")
        claimRequestId: Int
    ): Response<String>

    /*
     * ==========================================================
     * ADMIN - REJECT CLAIM REQUEST
     * ==========================================================
     */

    @PUT("api/claim-requests/{id}/reject")
    suspend fun rejectClaimRequest(
        @Path("id")
        claimRequestId: Int
    ): Response<String>

    /*
     * ==========================================================
     * ADMIN - UPDATE CLAIM STATUS
     * ==========================================================
     */

    @PUT("api/claim-requests/{id}/status")
    suspend fun updateClaimStatus(
        @Path("id")
        claimRequestId: Int,

        @Query("status")
        status: String
    ): Response<String>
}