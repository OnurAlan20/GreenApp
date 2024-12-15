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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.greenapp.components.BottomAppBar
import com.example.greenapp.components.EcoForumTopAppBar
import com.example.greenapp.components.Post
import com.example.greenapp.components.TopicCreationButton
import com.example.greenapp.model.PostsData
import com.example.greenapp.network.FireBaseAuthApi
import com.example.greenapp.network.FireBaseForumPostApi
import java.util.Date

@SuppressLint("NewApi")
@Composable
fun ForumPostScreen() {
    val firebaseForumPostApi = FireBaseForumPostApi()
    val context = LocalContext.current

    // State for holding the list of posts
    var posts by remember { mutableStateOf<List<PostsData>>(emptyList()) }

    // Fetch posts from Firestore when the screen loads
    LaunchedEffect(Unit) {
        firebaseForumPostApi.getPosts(
            onSuccess = { fetchedPosts ->
                posts = fetchedPosts
            },
            onFailure = { e ->
                Toast.makeText(context, "Veriler alınırken hata oluştu: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    // UI Structure
    Scaffold(
        bottomBar = {
            BottomAppBar(
                onChatClick = { /* AI Chat ekranına git */ },
                onForumClick = { /* Forum ekranına git */ },
                onSettingsClick = { /* Ayarlar ekranına git */ }
            )
        },
        topBar = {
            EcoForumTopAppBar { /* Top bar actions */ }
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
                // Post List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    contentPadding = PaddingValues(top = 16.dp)
                ) {
                    items(posts) { post ->
                        Post(post, firebaseForumPostApi,"uuid")
                    }
                }
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                    ) {
                    TopicCreationButton {
                        val newPost = PostsData(
                            userImage = "https://picsum.photos/400/400",
                            userName = "Onur",
                            text = "Bu bir örnek post.",
                            image = "https://picsum.photos/400/400",
                            likeCount = 0,
                            creationDate = Date()
                        )
                        firebaseForumPostApi.addPost(newPost,
                            onSuccess = {
                                Toast.makeText(context, "Post başarıyla eklendi!", Toast.LENGTH_SHORT).show()
                                firebaseForumPostApi.getPosts(
                                    onSuccess = { updatedPosts ->
                                        posts = updatedPosts
                                    },
                                    onFailure = { e ->
                                        Toast.makeText(context, "Veriler güncellenirken hata oluştu: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                                )
                            },
                            onFailure = { e ->
                                Toast.makeText(context, "Post eklenirken hata oluştu: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}