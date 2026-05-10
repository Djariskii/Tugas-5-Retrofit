package com.example.tugas_pasienapi.model

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?
)