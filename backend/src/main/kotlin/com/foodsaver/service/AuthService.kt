package com.foodsaver.service

import com.foodsaver.domain.User
import com.foodsaver.dto.AuthResponse
import com.foodsaver.dto.LoginRequest
import com.foodsaver.dto.RegisterRequest
import com.foodsaver.repository.UserRepository
import com.foodsaver.security.JwtUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {

    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already registered")
        }

        val user = User(
            name = request.name,
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password),
            role = request.role,
            phone = request.phone
        )

        val savedUser = userRepository.save(user)
        val token = jwtUtil.generateToken(savedUser.email)

        return AuthResponse(
            token = token,
            userId = savedUser.id!!,
            name = savedUser.name,
            email = savedUser.email,
            role = savedUser.role
        )
    }

    fun login(request: LoginRequest): AuthResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("Invalid credentials")

        if (!passwordEncoder.matches(request.password, user.passwordHash)) {
            throw IllegalArgumentException("Invalid credentials")
        }

        val token = jwtUtil.generateToken(user.email)

        return AuthResponse(
            token = token,
            userId = user.id!!,
            name = user.name,
            email = user.email,
            role = user.role
        )
    }
}
