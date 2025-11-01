#!/bin/bash

# FoodSaver API Test Script
# This script tests the basic functionality of the FoodSaver backend API

BASE_URL="http://localhost:8080"

echo "================================"
echo "FoodSaver API Test Script"
echo "================================"
echo ""

# Test 1: Register a seller
echo "Test 1: Register a seller"
echo "--------------------------"
REGISTER_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Seller",
    "email": "seller@test.com",
    "password": "test123",
    "role": "SELLER",
    "phone": "1234567890"
  }')

echo "$REGISTER_RESPONSE" | json_pp 2>/dev/null || echo "$REGISTER_RESPONSE"
echo ""

# Extract user ID and token
USER_ID=$(echo "$REGISTER_RESPONSE" | grep -o '"userId":[0-9]*' | grep -o '[0-9]*')
TOKEN=$(echo "$REGISTER_RESPONSE" | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

echo "User ID: $USER_ID"
echo ""

# Test 2: Login
echo "Test 2: Login"
echo "--------------"
LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "seller@test.com",
    "password": "test123"
  }')

echo "$LOGIN_RESPONSE" | json_pp 2>/dev/null || echo "$LOGIN_RESPONSE"
echo ""

# Test 3: Create a product
echo "Test 3: Create a product"
echo "------------------------"
PRODUCT_RESPONSE=$(curl -s -X POST "$BASE_URL/api/products" \
  -H "Content-Type: application/json" \
  -H "X-User-Id: $USER_ID" \
  -d '{
    "name": "Tomates",
    "price": 2500,
    "expirationDate": "2025-10-15"
  }')

echo "$PRODUCT_RESPONSE" | json_pp 2>/dev/null || echo "$PRODUCT_RESPONSE"
echo ""

# Test 4: List all products
echo "Test 4: List all products"
echo "-------------------------"
PRODUCTS_LIST=$(curl -s -X GET "$BASE_URL/api/products" \
  -H "X-User-Id: $USER_ID")

echo "$PRODUCTS_LIST" | json_pp 2>/dev/null || echo "$PRODUCTS_LIST"
echo ""

# Test 5: Register a buyer
echo "Test 5: Register a buyer"
echo "-------------------------"
BUYER_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Buyer",
    "email": "buyer@test.com",
    "password": "test123",
    "role": "BUYER",
    "phone": "0987654321"
  }')

echo "$BUYER_RESPONSE" | json_pp 2>/dev/null || echo "$BUYER_RESPONSE"
echo ""

echo "================================"
echo "API tests completed!"
echo "================================"
