package cit.edu.garol.campus.features.claimrequest.model

import com.google.gson.annotations.SerializedName

data class ClaimRequestItemReport(

    @SerializedName("id")
    val id: Int,

    @SerializedName("userId")
    val userId: String?,

    @SerializedName("reportType")
    val reportType: String,

    @SerializedName("itemName")
    val itemName: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("lastSeenLocation")
    val lastSeenLocation: String?,

    @SerializedName("imagePath")
    val imagePath: String?,

    @SerializedName("status")
    val status: String,

    @SerializedName("createdAt")
    val createdAt: String
)