package cit.edu.garol.campus.authentication.model

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val contactNumber: String,
    val email: String
)