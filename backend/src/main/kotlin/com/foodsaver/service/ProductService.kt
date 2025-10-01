package com.foodsaver.service

import com.foodsaver.domain.Product
import com.foodsaver.domain.ProductState
import com.foodsaver.dto.ProductCreateRequest
import com.foodsaver.dto.ProductResponse
import com.foodsaver.dto.ProductUpdateRequest
import com.foodsaver.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    fun createProduct(sellerId: Long, request: ProductCreateRequest): ProductResponse {
        val product = Product(
            sellerId = sellerId,
            name = request.name,
            price = request.price,
            imageUrl = request.imageUrl,
            expirationDate = request.expirationDate
        )

        val savedProduct = productRepository.save(product)
        return toProductResponse(savedProduct)
    }

    fun getAllProducts(): List<ProductResponse> {
        return productRepository.findAll().map { toProductResponse(it) }
    }

    fun getProductById(id: Long): ProductResponse {
        val product = productRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Product not found") }
        return toProductResponse(product)
    }

    fun getProductsBySeller(sellerId: Long): List<ProductResponse> {
        return productRepository.findBySellerId(sellerId).map { toProductResponse(it) }
    }

    fun getProductsByState(state: ProductState): List<ProductResponse> {
        return productRepository.findByState(state).map { toProductResponse(it) }
    }

    fun updateProduct(id: Long, sellerId: Long, request: ProductUpdateRequest): ProductResponse {
        val product = productRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Product not found") }

        if (product.sellerId != sellerId) {
            throw IllegalArgumentException("Not authorized to update this product")
        }

        val updatedProduct = product.copy(
            name = request.name ?: product.name,
            price = request.price ?: product.price,
            state = request.state ?: product.state,
            imageUrl = request.imageUrl ?: product.imageUrl,
            expirationDate = request.expirationDate ?: product.expirationDate
        )

        val savedProduct = productRepository.save(updatedProduct)
        return toProductResponse(savedProduct)
    }

    fun deleteProduct(id: Long, sellerId: Long) {
        val product = productRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Product not found") }

        if (product.sellerId != sellerId) {
            throw IllegalArgumentException("Not authorized to delete this product")
        }

        productRepository.delete(product)
    }

    private fun toProductResponse(product: Product): ProductResponse {
        return ProductResponse(
            id = product.id!!,
            sellerId = product.sellerId,
            name = product.name,
            price = product.price,
            state = product.state,
            imageUrl = product.imageUrl,
            expirationDate = product.expirationDate
        )
    }
}
