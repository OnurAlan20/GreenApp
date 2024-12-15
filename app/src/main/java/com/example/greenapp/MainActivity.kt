package com.example.greenapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.greenapp.network.FireBaseAuthApi
import com.example.greenapp.screens.ForumPostScreen
import com.example.greenapp.screens.ForumTopicScreen
import com.example.greenapp.ui.theme.GreenAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreenAppTheme {
                println(FireBaseAuthApi.getLoggedInUser())
                ForumPostScreen()
            }
        }
    }
}
