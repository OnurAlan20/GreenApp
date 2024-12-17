package com.example.greenapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun UserImagePP(imageUrl:String,size:Dp = 40.dp){
    AsyncImage(
        model = imageUrl,
        contentDescription = "Profile Picture",
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.LightGray) // Placeholder rengi
    )
}