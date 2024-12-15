package com.example.greenapp.network

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.greenapp.model.PostsData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.File
import java.io.IOException


class FireBaseForumPostApi {

    private val firestore = FirebaseFirestore.getInstance()

    fun addPost(
        post: PostsData,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
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

    fun uploadImageToImgBB(
        file: File,
        fileName:String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val url = "https://api.imgbb.com/1/upload?key=d6ac6a2edb0316131b0373c7bca3d277"

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "image",
                "$fileName",
                RequestBody.create("image/*".toMediaTypeOrNull(), file)
            )
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFailure(e.message ?: "Unknown error")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val imageUrl = extractImageUrlFromResponse(responseBody)
                    onSuccess(imageUrl)
                } else {
                    onFailure("Error: ${response.message}")
                }
            }
        })
    }

    fun extractImageUrlFromResponse(response: String?): String {
        val jsonObject = JSONObject(response ?: "{}")
        return jsonObject.getJSONObject("data").getString("url")
    }


    fun uriToFile(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver
        val tempFile = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                tempFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            return tempFile
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

}


