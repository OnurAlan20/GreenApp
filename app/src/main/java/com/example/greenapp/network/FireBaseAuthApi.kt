package com.example.greenapp.network

import com.example.greenapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

public object FireBaseAuthApi {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var loggedInUser: UserModel

    /**
     * Register a new user with email and password and save user data in Firestore.
     */
    fun register(userName: String, email: String, password: String,onComplete:()->Unit,onFailed:(err:String)->Unit) {
            // Create user with Firebase Authentication
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                it ->
                val createdDate = getCurrentDate()
                val user = UserModel(
                    userName = userName,
                    email = email,
                    id = it.user!!.uid,
                    createdDate = createdDate
                )
                // Save the user data in Firestore
                firestore.collection("users").document(it.user!!.uid).set(user).addOnSuccessListener {
                    println("Firestore Success")
                    onComplete()
                }.addOnFailureListener {
                    println(it.localizedMessage)
                    onFailed(it.localizedMessage)
                }

            }.addOnFailureListener {
                it->
                println(it.localizedMessage)
                onFailed(it.localizedMessage)

            }     // Create a UserModel object



    }

    /**
     * Login a user with email and password.
     */
    suspend fun login(email: String, password: String,onSuccess:()->Unit,onFailed: (err: String) -> Unit){

        val a = firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            onSuccess()
            firebaseUser = it.user!!
        }.addOnFailureListener {
            onFailed(it.localizedMessage)
        }


    }

    /**
     * Get the current date in "yyyy-MM-dd HH:mm:ss" format.
     */
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }
     fun getLoggedInUser():String{
        return firebaseUser.uid
    }
}