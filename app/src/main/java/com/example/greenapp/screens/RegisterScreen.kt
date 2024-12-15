package com.example.greenapps.screens
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenapp.R
import com.example.greenapp.network.FireBaseAuthApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun RegisterScreen(onLoginClick: () -> Unit) {
    val context = LocalContext.current // Toast için context gerekiyor
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }
    var toastText by remember { mutableStateOf("") }
    val greenColor = Color(0xFF00C853)

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Scroll özelliği eklendi
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // App logo or circular icon placeholder
            Image(
                painter = painterResource(id = R.drawable.loginlogo), // Replace with your logo resource
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(150.dp) // Daha uygun bir boyut için küçültüldü
                    .padding(16.dp)
            )

            Text(
                text = "Create Your Account on Green App",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "Welcome to Green App! Join us today to enjoy a seamless experience.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Username field
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painterResource(id = if (passwordVisible) R.drawable.visibility else R.drawable.visiblityoff),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            modifier = Modifier.size(26.dp)
                        )
                    }
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Confirm Password field
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            painterResource(id = if (confirmPasswordVisible) R.drawable.visibility else R.drawable.visiblityoff),
                            contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
                            modifier = Modifier.size(26.dp)
                        )
                    }
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Terms and Conditions Checkbox
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isChecked, onCheckedChange = { isChecked = it })
                Text(
                    text = "I agree to the Terms and Conditions",
                    modifier = Modifier.padding(start = 4.dp),
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            if (showToast){
                Toast.makeText(context, "Error: " + toastText, Toast.LENGTH_LONG).show()
                showToast = false
            }

            // Register Button
            Button(
                onClick = {

                        if (password == confirmPassword) {
                            if (isChecked) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    try {
                                        val regResult = FireBaseAuthApi.register(username, email, password, onComplete = {
                                            Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                                            onLoginClick() // Navigate to the login screen
                                        }, onFailed = {it->
                                            toastText = it
                                            showToast = true
                                        })
                                        println("Register Send")

                                    } catch (e: Exception) {
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                                        }
                                        println("Registration error: ${e.printStackTrace()}")
                                    }
                                }
                            } else {
                                Toast.makeText(context, "Please accept the Terms and Conditions.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                        }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = greenColor)
            ) {
                Text(text = "Register", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Login link
            Row {
                Text(
                    text = "Already have an account? Log In",
                    modifier = Modifier.clickable(onClick = {onLoginClick()}),
                    color = Color.Gray
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRegisterScreen(){
    RegisterScreen{}
}