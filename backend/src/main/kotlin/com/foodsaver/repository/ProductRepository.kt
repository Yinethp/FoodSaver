package com.foodsaver.repository

import com.foodsaver.domain.Product
import com.foodsaver.domain.ProductState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findBySellerId(sellerId: Long): List<Product>
    fun findByState(state: ProductState): List<Product>
}
