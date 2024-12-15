package com.example.greenapp.network

import android.util.Log
import com.example.greenapp.model.PostsData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects


class FireBaseForumPostApi {

    private val firestore = FirebaseFirestore.getInstance()

    fun addPost(
        post: PostsData,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        FireBaseAuthApi.getUserData(
            onSuccess = { user ->
                val updatedPost = post.copy(userName = user.userName)

                firestore.collection("posts")
                    .add(updatedPost)
                    .addOnSuccessListener {
                        Log.d("Firebase", "Post başarıyla eklendi!")
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firebase", "Post eklenirken hata oluştu.", e)
                        onFailure(e)
                    }
            },
            onFail = { error ->
                Log.e("Firebase", "Kullanıcı verisi alınamadı: $error")
                onFailure(Exception("Kullanıcı verisi alınamadı: $error"))
            }
        )
    }



    fun getPosts(onSuccess: (List<PostsData>) -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("posts")
            .get()
            .addOnSuccessListener { snapshot ->
                val posts = snapshot.toObjects<PostsData>()
                onSuccess(posts)
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Veriler alınırken hata oluştu.", e)
                onFailure(e)
            }
    }

    fun updateLikeStatus(
        postId: String,
        userId: String,
        isLiked: Boolean,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val postRef = FirebaseFirestore.getInstance().collection("posts").document(postId)

        postRef.get()
            .addOnSuccessListener { document ->
                val likedUsers = document.get("likedUsers") as? List<String> ?: emptyList()
                val updatedLikedUsers = if (isLiked) {
                    likedUsers + userId
                } else {
                    likedUsers - userId
                }

                postRef.update(
                    mapOf(
                        "likedUsers" to updatedLikedUsers,
                        "likeCount" to updatedLikedUsers.size.toLong()
                    )
                ).addOnSuccessListener {
                    onSuccess()
                }.addOnFailureListener { e ->
                    onFailure(e)
                }
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }
}


