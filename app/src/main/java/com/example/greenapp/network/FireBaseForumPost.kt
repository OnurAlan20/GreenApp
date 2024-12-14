package com.example.greenapp.network

import android.util.Log
import com.example.greenapp.model.PostsData
import com.google.firebase.firestore.FirebaseFirestore

class FireBaseForumPost {

    private val firestore = FirebaseFirestore.getInstance()

    fun addPost(post: PostsData, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("posts")
            .add(post)
            .addOnSuccessListener {
                Log.d("Firebase", "Post başarıyla eklendi!")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Post eklenirken hata oluştu.", e)
                onFailure(e)
            }
    }
}
