package com.foodsaver.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 150)
    val name: String,

    @Column(nullable = false, unique = true, length = 150)
    val email: String,

    @Column(nullable = false, name = "password_hash", length = 255)
    var passwordHash: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: UserRole,

    @Column(length = 30)
    val phone: String? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)

enum class UserRole {
    SELLER,
    BUYER,
    ADMIN
}
