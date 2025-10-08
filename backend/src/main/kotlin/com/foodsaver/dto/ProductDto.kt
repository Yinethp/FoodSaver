package com.foodsaver.dto

import com.foodsaver.domain.ProductState
import java.math.BigDecimal
import java.time.LocalDate

data class ProductCreateRequest(
    val name: String,
    val price: BigDecimal? = null,
    val imageUrl: String? = null,
    val expirationDate: LocalDate? = null
)

data class ProductUpdateRequest(
    val name: String? = null,
    val price: BigDecimal? = null,
    val state: ProductState? = null,
    val imageUrl: String? = null,
    val expirationDate: LocalDate? = null
)

data class ProductResponse(
    val id: Long,
    val sellerId: Long,
    val name: String,
    val price: BigDecimal?,
    val state: ProductState,
    val imageUrl: String?,
    val expirationDate: LocalDate?
)
