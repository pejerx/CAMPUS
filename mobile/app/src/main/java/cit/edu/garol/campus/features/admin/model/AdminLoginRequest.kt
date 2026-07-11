package cit.edu.garol.campus.features.admin.model

data class AdminLoginRequest(
    val emailOrId: String,
    val password: String
)