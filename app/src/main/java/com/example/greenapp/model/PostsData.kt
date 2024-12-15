package com.example.greenapp.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class PostsData(
    @DocumentId val id: String = "",
    @PropertyName("userImage") val userImage: String = "",
    @PropertyName("userName") val userName: String = "",
    @PropertyName("text") val text: String = "",
    @PropertyName("image") val image: String = "",
    @PropertyName("likeCount") val likeCount: Long = 0,
    @PropertyName("likedUsers") val likedUsers: List<String> = emptyList(),
    @ServerTimestamp @PropertyName("creationDate") val creationDate: Date? = null
)
