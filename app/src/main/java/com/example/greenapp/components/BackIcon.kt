package com.example.greenapp.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


@Composable
fun BackIcon(navHostController: NavHostController){
    Icon(
        imageVector = Icons.Sharp.KeyboardArrowLeft, // Menü ikonu
        contentDescription = "Menu",
        modifier = Modifier.clickable {
            navHostController.navigate("forum_posts")
        }
    )
}