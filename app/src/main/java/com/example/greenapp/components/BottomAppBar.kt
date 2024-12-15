package com.example.greenapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenapp.R

@Composable
fun BottomAppBar(
    onChatClick: () -> Unit,
    onForumClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    BottomAppBar(
        modifier = Modifier.height(80.dp),
        containerColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 7.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                IconButton(onClick = onChatClick, modifier = Modifier.fillMaxSize()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.chat_filled_icon),
                            contentDescription = "AI Chat",
                            modifier = Modifier.size(23.dp)
                        )
                        Text(
                            text = "Chat",
                            fontSize = 12.sp,
                            color = Color.Black,
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                IconButton(onClick = onForumClick, modifier = Modifier.fillMaxSize()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.forum_filled_icon),
                            contentDescription = "Forum",
                            modifier = Modifier
                                .size(23.dp)
                                .fillMaxHeight(),

                            )
                        Text(
                            text = "Forum",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black // Renk ayarı
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                IconButton(onClick = onSettingsClick, modifier = Modifier.fillMaxSize()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.setting),
                            contentDescription = "Settings",
                            modifier = Modifier
                                .size(23.dp)
                                .fillMaxHeight()
                        )
                        Text(
                            text = "Settings",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}



