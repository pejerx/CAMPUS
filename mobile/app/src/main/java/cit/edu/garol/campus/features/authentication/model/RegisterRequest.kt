package cit.edu.garol.campus.features.authentication.model

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val role: String,
    val contactNumber: String,
    val email: String,
    val password: String
)