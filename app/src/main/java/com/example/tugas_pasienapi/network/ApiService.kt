package com.example.tugas_pasienapi.network

import com.example.tugas_pasienapi.model.ApiResponse
import com.example.tugas_pasienapi.model.LoginData
import com.example.tugas_pasienapi.model.LoginRequest
import com.example.tugas_pasienapi.model.Pasien
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    // Endpoint Login
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<ApiResponse<LoginData>>

    // Endpoint Pasien (Wajib pakai Header Bearer Token)
    @GET("pasien")
    suspend fun getPasien(
        @Header("Authorization") token: String
    ): Response<ApiResponse<List<Pasien>>>
}