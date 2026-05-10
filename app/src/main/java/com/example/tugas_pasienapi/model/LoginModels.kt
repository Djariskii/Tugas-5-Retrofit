package com.example.tugas_pasienapi.model
data class LoginRequest(
    val email: String,
    val password: String
)

data class User(
    val name: String,
    val email: String
)

data class LoginData(
    val token: String,
    val user: User
)