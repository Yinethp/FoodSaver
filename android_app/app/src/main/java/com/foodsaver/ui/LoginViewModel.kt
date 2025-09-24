package com.foodsaver.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodsaver.data.model.LoginRequest
import com.foodsaver.data.model.LoginResponse
import com.foodsaver.network.ApiClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val _token = MutableStateFlow<String?>(null)
    val token = _token.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response: LoginResponse = ApiClient.authApi.login(
                    LoginRequest(username, password)
                )
                _token.value = response.token
            } catch (e: Exception) {
                _token.value = "Error: ${e.message}"
            }
        }
    }
}
