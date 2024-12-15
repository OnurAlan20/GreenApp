package com.example.greenapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.greenapp.screens.ForumPostCreateScreen
import com.example.greenapp.screens.ForumPostScreen
import com.example.greenapp.screens.ForumTopicCreateScreen
import com.example.greenapp.screens.ForumTopicScreen
import com.example.greenapp.screens.SettingsScreen
import com.example.greenapp.screens.SettingsScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "forum_posts") {
        composable("forum_posts") {
            ForumPostScreen(navController)
        }
        composable("forum_topics") {
            ForumTopicScreen(navController)
        }
        composable("create_post") {
            ForumPostCreateScreen(navController)
        }
        composable("create_topic") {
            ForumTopicCreateScreen(navController)
        }
        composable("settings_menu") {
            SettingsScreen(navController)
        }
    }
}
