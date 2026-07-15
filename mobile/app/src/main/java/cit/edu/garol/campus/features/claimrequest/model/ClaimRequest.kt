package cit.edu.garol.campus.features.claimrequest.model

import com.google.gson.annotations.SerializedName

data class ClaimRequest(

    @SerializedName("id")
    val id: Int,

    @SerializedName("claimantId")
    val claimantId: String,

    @SerializedName("claimantName")
    val claimantName: String,

    @SerializedName("claimantEmail")
    val claimantEmail: String,

    @SerializedName("claimantPhone")
    val claimantPhone: String,

    @SerializedName("proofImagePath")
    val proofImagePath: String?,

    @SerializedName("itemDescription")
    val itemDescription: String,

    @SerializedName("additionalInformation")
    val additionalInformation: String?,

    @SerializedName("status")
    val status: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("itemReport")
    val itemReport: ClaimRequestItemReport
)