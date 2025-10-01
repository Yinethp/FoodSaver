package com.foodsaver.controller

import com.foodsaver.domain.ProductState
import com.foodsaver.dto.ProductCreateRequest
import com.foodsaver.dto.ProductResponse
import com.foodsaver.dto.ProductUpdateRequest
import com.foodsaver.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService
) {

    @PostMapping
    fun createProduct(
        @RequestHeader("X-User-Id") sellerId: Long,
        @RequestBody request: ProductCreateRequest
    ): ResponseEntity<ProductResponse> {
        return try {
            val response = productService.createProduct(sellerId, request)
            ResponseEntity.status(HttpStatus.CREATED).body(response)
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping
    fun getAllProducts(@RequestParam(required = false) state: ProductState?): ResponseEntity<List<ProductResponse>> {
        val products = if (state != null) {
            productService.getProductsByState(state)
        } else {
            productService.getAllProducts()
        }
        return ResponseEntity.ok(products)
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<ProductResponse> {
        return try {
            val response = productService.getProductById(id)
            ResponseEntity.ok(response)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/seller/{sellerId}")
    fun getProductsBySeller(@PathVariable sellerId: Long): ResponseEntity<List<ProductResponse>> {
        val products = productService.getProductsBySeller(sellerId)
        return ResponseEntity.ok(products)
    }

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestHeader("X-User-Id") sellerId: Long,
        @RequestBody request: ProductUpdateRequest
    ): ResponseEntity<ProductResponse> {
        return try {
            val response = productService.updateProduct(id, sellerId, request)
            ResponseEntity.ok(response)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: Long,
        @RequestHeader("X-User-Id") sellerId: Long
    ): ResponseEntity<Void> {
        return try {
            productService.deleteProduct(id, sellerId)
            ResponseEntity.noContent().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}
