package com.example.greenapp.model

import androidx.compose.ui.graphics.painter.Painter

data class TopicData(
    val image: Painter,
    val topicTitle: String,
    val userName: String,
    val isLiked: Boolean,
)
