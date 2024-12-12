package com.example.greenapp.model

import androidx.compose.ui.graphics.painter.Painter
import java.time.LocalDateTime

data class PostsData(
    val userName: String,
    val creationDate: LocalDateTime,
    val text: String,
    val image: Painter,
    val LikeCount: Long)
