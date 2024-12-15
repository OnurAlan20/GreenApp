package com.example.greenapp.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.greenapp.MainActivity
import com.example.greenapp.R
import com.example.greenapp.network.FireBaseAuthApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun LoginScreen(
    onRegisterClick:()->Unit
) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    var toastText by remember {
        mutableStateOf("")
    }
    var showToast by remember {
        mutableStateOf(false)
    }
    val greenColor = Color(0xFF00C853)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Logo placeholder
        Image(
            painter = painterResource(id = R.drawable.loginlogo), // Replace with your image resource
            contentDescription = "Green App Logo",
            modifier = Modifier
                .size(200.dp)

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Welcome to Green App",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "Your gateway to a greener future.",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Email Input
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                    Icon(
                        painterResource(id = if (passwordVisibility.value) R.drawable.visibility else R.drawable.visiblityoff),
                        contentDescription = if (passwordVisibility.value) "Hide password" else "Show password"
                        ,Modifier.size(26.dp))
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        // Login Button
        if (showToast){
            Toast.makeText(context,toastText,Toast.LENGTH_SHORT).show()
            showToast = false
        }
        Button(
            onClick = {
                GlobalScope.launch(Dispatchers.IO) {
                    val result = FireBaseAuthApi.login(email=email.value,password=password.value, onSuccess = {
                        toastText = "Login Success!"
                        showToast = true
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(context,intent,null) // This starts MainActivity

                    }, onFailed = {
                        toastText = it
                        showToast = true
                    })

                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = greenColor)
        ) {
            Text(text = "Login", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Forgot Password




        // Sign Up
        Row {
            Text(
                text = "Don't have an account? Sign Up",
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.clickable {
                    System.out.println("REGISTERRRR")
                    onRegisterClick()
                }
            )
        }
    }
}
