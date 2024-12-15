package com.example.greenapp.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.greenapp.R
import com.example.greenapp.components.BottomAppBar
import com.example.greenapp.components.EcoForumTopAppBar
import com.example.greenapp.components.Post
import com.example.greenapp.components.TopicCreationButton
import com.example.greenapp.model.PostsData
import com.example.greenapp.network.FireBaseAuthApi
import com.example.greenapp.network.FireBaseForumPostApi

@SuppressLint("NewApi")
@Composable
fun ForumPostScreen(navController: NavHostController) {
    val firebaseForumPostApi = FireBaseForumPostApi()
    val context = LocalContext.current

    var posts by remember { mutableStateOf<List<PostsData>>(emptyList()) }

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

    Scaffold(
        bottomBar = {
            BottomAppBar(
                onChatClick = { navController.navigate("forum_topics")},
                onForumClick = { navController.navigate("forum_posts") },
                onSettingsClick = { navController.navigate("forum_topics") }
            )
        },
        topBar = {
            EcoForumTopAppBar(
                iconId = R.drawable.menu_icon,
                title = "Green Forum"
            ) {
                navController.navigate("forum_posts")
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    contentPadding = PaddingValues(top = 16.dp)
                ) {
                    items(posts) { post ->
                        Post(post, firebaseForumPostApi, FireBaseAuthApi.getLoggedInUser())
                    }
                }
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                    ) {
                    TopicCreationButton {
                        navController.navigate("create_post")
                    }
                }
            }
        }
    }
}