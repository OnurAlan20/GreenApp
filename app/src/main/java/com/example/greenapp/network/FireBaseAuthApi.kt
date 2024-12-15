package com.example.greenapp.network

import com.example.greenapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FireBaseAuthApi {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    /**
     * Register a new user with email and password and save user data in Firestore.
     */
    suspend fun register(userName: String, email: String, password: String): Result<UserModel> {
        return try {
            // Create user with Firebase Authentication
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: throw Exception("User ID is null")

            // Create a UserModel object
            val createdDate = getCurrentDate()
            val user = UserModel(
                userName = userName,
                email = email,
                id = userId,
                createdDate = createdDate
            )

            // Save the user data in Firestore
            firestore.collection("users").document(userId).set(user).await()

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Login a user with email and password.
     */
    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get the current date in "yyyy-MM-dd HH:mm:ss" format.
     */
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }
}