package com.example.greenapp.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AIService {
    suspend fun sendMessage(newMessage: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.sendMessage(
                    ChatRequest(
                        messages = listOf(Message(role = "user", content = newMessage))
                    )
                )
                response.choices.firstOrNull()?.message?.content ?: "Boş cevap döndü!"
            } catch (e: Exception) {
                "Hata oluştu: ${e.message}"
            }
        }
    }
}