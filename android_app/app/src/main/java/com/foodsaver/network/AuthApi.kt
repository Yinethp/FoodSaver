package com.foodsaver.network

import com.foodsaver.data.model.LoginRequest
import com.foodsaver.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}
