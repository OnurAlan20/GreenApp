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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.greenapp.screens.LoginScreen
import com.example.greenapp.ui.theme.GreenAppTheme
import com.example.greenapps.screens.RegisterScreen

class LoginRegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreenAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { it ->
                    println(it.calculateTopPadding())
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") {
                            LoginScreen(onRegisterClick = { navController.navigate("register") })
                        }
                        composable("register") {
                            RegisterScreen(onLoginClick = { navController.navigate("login") })
                        }
                    }
                }
            }
        }
    }
}
