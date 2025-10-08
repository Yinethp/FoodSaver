package com.foodsaver.dto

import com.foodsaver.domain.UserRole

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: UserRole,
    val phone: String? = null
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val userId: Long,
    val name: String,
    val email: String,
    val role: UserRole
)
