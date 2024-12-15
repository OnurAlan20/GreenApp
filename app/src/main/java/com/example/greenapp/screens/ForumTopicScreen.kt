package com.example.greenapp.screens

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
import androidx.navigation.NavHostController
import com.example.greenapp.R
import com.example.greenapp.components.BottomAppBar
import com.example.greenapp.components.EcoForumTopAppBar
import com.example.greenapp.components.Topic
import com.example.greenapp.components.TopicCreationButton
import com.example.greenapp.model.TopicData

@Composable
fun ForumTopicScreen(navController: NavHostController) {

    val topics = listOf(
        TopicData(
            image = painterResource(R.drawable.ic_launcher_background),
            topicTitle = "Küresel Isınma",
            userName = "Onur Alan",
            isLiked = true
        ),
        TopicData(
            image = painterResource(R.drawable.ic_launcher_background),
            topicTitle = "Plastik Atıklar",
            userName = "GreenWarrior123",
            isLiked = false
        ),
        TopicData(
            image = painterResource(R.drawable.ic_launcher_background),
            topicTitle = "Sürdürülebilir Moda",
            userName = "Fashionista202",
            isLiked = true
        ),
        TopicData(
            image = painterResource(R.drawable.ic_launcher_background),
            topicTitle = "Sürdürülebilir Moda",
            userName = "Fashionista202",
            isLiked = true
        ),
        TopicData(
            image = painterResource(R.drawable.ic_launcher_background),
            topicTitle = "Sürdürülebilir Moda",
            userName = "Fashionista202",
            isLiked = true
        ),
        TopicData(
            image = painterResource(R.drawable.ic_launcher_background),
            topicTitle = "Sürdürülebilir Moda",
            userName = "Fashionista202",
            isLiked = true
        ),
        TopicData(
            image = painterResource(R.drawable.ic_launcher_background),
            topicTitle = "Sürdürülebilir Moda",
            userName = "Fashionista202",
            isLiked = true
        ),
        TopicData(
            image = painterResource(R.drawable.ic_launcher_background),
            topicTitle = "Sürdürülebilir Moda",
            userName = "Fashionista202",
            isLiked = true
        ),
        TopicData(
            image = painterResource(R.drawable.ic_launcher_background),
            topicTitle = "Sürdürülebilir Moda",
            userName = "Fashionista202",
            isLiked = true
        ),
        TopicData(
            image = painterResource(R.drawable.ic_launcher_background),
            topicTitle = "Sürdürülebilir Moda",
            userName = "Fashionista202",
            isLiked = true
        ),
    )

    Scaffold(
        bottomBar = {
            BottomAppBar(
                onChatClick = { navController.navigate("forum_topics")},
                onForumClick = { navController.navigate("forum_posts") },
                onSettingsClick = { navController.navigate("forum_topics") }
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
                        items(topics) { topic ->
                            Topic(
                                image = topic.image,
                                topicTitle = topic.topicTitle,
                                userName = topic.userName,
                                isLiked = topic.isLiked
                            ) {
                                println("Beğeni durumu değişti: ${topic.topicTitle}")
                            }
                        }
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