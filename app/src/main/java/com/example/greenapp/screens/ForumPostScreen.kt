package com.example.greenapp.screens

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.greenapp.R
import com.example.greenapp.components.BottomAppBar
import com.example.greenapp.components.EcoForumTopAppBar
import com.example.greenapp.components.Post
import com.example.greenapp.components.TopicCreationButton
import com.example.greenapp.model.PostsData
import com.example.greenapp.network.FireBaseForumPost
import java.time.LocalDateTime
import java.util.Date

@SuppressLint("NewApi")
@Composable
fun ForumPostScreen() {
    val firebaseForumPost = FireBaseForumPost()
    val context = LocalContext.current
    val posts:List<PostsData> = listOf()

    val newPost = PostsData(
        userImage = "https://example.com/user_image.jpg",
        userName = "Onur",
        text = "Bu bir örnek post.",
        image = "https://example.com/post_image.jpg",
        likeCount = 0,
        creationDate = Date(1233)
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
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.88f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(top = 16.dp)
                    ) {
                        items(posts) { post ->
                            Post(post)
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    TopicCreationButton {
                        firebaseForumPost.addPost(newPost,
                            onSuccess = {
                                Toast.makeText(context, "Post başarıyla eklendi!", Toast.LENGTH_SHORT).show()
                            },
                            onFailure = { e ->
                                Toast.makeText(context, "Hata: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}