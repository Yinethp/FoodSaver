package com.foodsaver.domain

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "seller_id", nullable = false)
    val sellerId: Long,

    @Column(nullable = false, length = 200)
    val name: String,

    @Column(precision = 10, scale = 2)
    val price: BigDecimal? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val state: ProductState = ProductState.AVAILABLE,

    @Column(name = "image_url", length = 500)
    val imageUrl: String? = null,

    @Column(name = "expiration_date")
    val expirationDate: LocalDate? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)

enum class ProductState {
    AVAILABLE,
    SOLD,
    DONATED,
    EXPIRED
}
