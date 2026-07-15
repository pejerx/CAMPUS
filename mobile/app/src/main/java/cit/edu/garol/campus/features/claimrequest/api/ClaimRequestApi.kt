package cit.edu.garol.campus.features.claimrequest.api

import cit.edu.garol.campus.features.claimrequest.model.ClaimRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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

    /*
 * ==========================================================
 * USER - SUBMIT CLAIM REQUEST
 * ==========================================================
 */

    @Multipart
    @POST("api/claim-requests")
    suspend fun submitClaimRequest(

        @Part("itemReportId")
        itemReportId: RequestBody,

        @Part("claimantId")
        claimantId: RequestBody,

        @Part("claimantName")
        claimantName: RequestBody,

        @Part("claimantEmail")
        claimantEmail: RequestBody,

        @Part("claimantPhone")
        claimantPhone: RequestBody,

        @Part("itemDescription")
        itemDescription: RequestBody,

        @Part("additionalInformation")
        additionalInformation: RequestBody,

        @Part
        proofImage: MultipartBody.Part?

    ): Response<ClaimRequest>

}