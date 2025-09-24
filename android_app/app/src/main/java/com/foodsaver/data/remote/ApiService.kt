package com.foodsaver.data.remote

import retrofit2.http.*
import retrofit2.Response

data class LoginRequest(val email: String, val password: String)
data class AuthResponse(val accessToken: String, val refreshToken: String)

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body req: LoginRequest): Response<AuthResponse>

    @GET("api/products")
    suspend fun getProducts(): Response<List<Map<String, Any>>>
}
