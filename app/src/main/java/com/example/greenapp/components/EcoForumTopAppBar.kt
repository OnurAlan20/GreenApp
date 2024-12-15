@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.greenapp.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun EcoForumTopAppBar(iconId:Int,title:String,onIconClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Menü İkonu
                IconButton(onClick = onIconClick) {
                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription = "Menu",
                        tint = Color.Black,)
                }
                // Başlık
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        },
    )
}
