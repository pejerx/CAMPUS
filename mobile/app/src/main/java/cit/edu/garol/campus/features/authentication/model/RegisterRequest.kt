package cit.edu.garol.campus.model

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val role: String,
    val contactNumber: String,
    val email: String,
    val password: String
)