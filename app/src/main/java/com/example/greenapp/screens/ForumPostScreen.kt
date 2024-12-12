package com.example.greenapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.greenapp.R
import com.example.greenapp.components.BottomAppBar
import com.example.greenapp.components.EcoForumTopAppBar
import com.example.greenapp.components.Topic
import com.example.greenapp.components.TopicCreationButton
import com.example.greenapp.model.PostsData
import java.time.LocalDateTime

@SuppressLint("NewApi")
@Composable
fun ForumPostScreen(){
    val posts = listOf(
        PostsData(
            userName = "onur",
            creationDate = LocalDateTime.now(),
            text = "lormsdnklamnfkasd apksdmkoamdfak apsdkpasld apdmaspd",
            image = painterResource(R.drawable.ic_launcher_background),
            LikeCount = 100L
        ),
        PostsData(
            userName = "onur",
            creationDate = LocalDateTime.now(),
            text = "lormsdnklamnfkasd apksdmkoamdfak apsdkpasld apdmaspd",
            image = painterResource(R.drawable.ic_launcher_background),
            LikeCount = 100L
        ),
        PostsData(
            userName = "onur",
            creationDate = LocalDateTime.now(),
            text = "lormsdnklamnfkasd apksdmkoamdfak apsdkpasld apdmaspd",
            image = painterResource(R.drawable.ic_launcher_background),
            LikeCount = 100L
        ),
    )

    Scaffold(
        bottomBar = {
            BottomAppBar(
                onChatClick = { /* AI Chat ekranına git */ },
                onForumClick = { /* Forum ekranına git */ },
                onSettingsClick = { /* Ayarlar ekranına git */ }
            )
        },
        topBar = {
            EcoForumTopAppBar {
            }
        }
    ) { innerPadding ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.surface)
        ) {
            Column (modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ){
                Column (modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight(0.88f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(top = 16.dp)
                    ) {
                        //todo
                    }
                }
                Column (modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight(1f),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Bottom
                ){
                    TopicCreationButton {

                    }
                }
            }
        }
    }
}