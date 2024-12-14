package com.example.greenapp.model

import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class PostsData(
    @PropertyName("user_image") @JvmField val userImage: String = "",
    @PropertyName("user_name") @JvmField val userName: String = "",
    @ServerTimestamp @PropertyName("creation_date") @JvmField val creationDate: Date? = null,
    @PropertyName("text") @JvmField val text: String = "",
    @PropertyName("image") @JvmField val image: String = "",
    @PropertyName("like_count") @JvmField val likeCount: Long = 0
)
