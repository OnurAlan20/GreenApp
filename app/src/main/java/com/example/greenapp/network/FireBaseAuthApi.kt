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
    fun register(firstName:String,lastName:String,phoneNumber:String,userName: String, email: String, password: String,onComplete:()->Unit,onFailed:(err:String)->Unit) {
            // Create user with Firebase Authentication
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                it ->
                val createdDate = getCurrentDate()
                val user = UserModel(
                    userName = userName,
                    email = email,
                    id = it.user!!.uid,
                    createdDate = createdDate,
                    firstName=firstName,
                    lastName=lastName,
                    phoneNumber=phoneNumber
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

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }
    fun getLoggedInUser():String{
        return firebaseUser.uid
    }
    suspend fun getUserData(onFail:(e:String)->Unit={}):MutableMap<String,Any>?{
        val data = firestore.collection("users").document(getLoggedInUser()).get().await()
        return data.data
    }
    suspend fun updateUserData(userName:String,lastName:String,firstName:String,phoneNumber: String,email: String,onFail:(e:String)->Unit={},onSuccess: () -> Unit){
        val updatedData = mapOf(
            "userName" to userName,
            "lastName" to lastName,
            "firstName" to firstName,
            "phoneNumber" to phoneNumber,
            "email" to email
        )
        firestore.collection("users").document(getLoggedInUser()).update(updatedData).addOnFailureListener {
            onFail(it.localizedMessage)
        }.addOnSuccessListener {
            onSuccess()
        }

    }
}