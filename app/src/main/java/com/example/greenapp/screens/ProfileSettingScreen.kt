package com.example.greenapp.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.greenapp.R
import com.example.greenapp.network.FireBaseAuthApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileSettingScreen(navHostController: NavHostController = rememberNavController()){
    val navController = rememberNavController()
    val context = LocalContext.current
    var username by remember { mutableStateOf("EmilyJ") }
    var email by remember { mutableStateOf("emily.j@example.com") }
    var phoneNumber by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }
    val greenColor = Color(0xFF00C853)
    GlobalScope.launch(Dispatchers.IO) {
        val data = FireBaseAuthApi.getUserData {  }
        username= data?.get("userName").toString()
        email = data?.get("email").toString()
        phoneNumber = data?.get("phoneNumber").toString()
        firstName = data?.get("firstName").toString()
        lastName = data?.get("lastName").toString()
    }
    Scaffold (topBar = { SettingsTopAppBar(title = "Profile Settings", pos = 0.25f) }, bottomBar = { SettingsBottomAppBar(navController) }){
            it->
        Surface(modifier = Modifier.fillMaxSize(1f).padding(it).background(Color(0xFFE8E8E8))) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // Profile Image
                AsyncImage(
                    model = "", // Replace with actual URL
                    contentDescription = "Profile Picture",
                    placeholder = painterResource(id = R.drawable.place_holder_pp), // Add a placeholder resource
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Profile Name
                Text(
                    text = firstName + " " + lastName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Username handle
                Text(
                    text = "@"+username,
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Username Input
                TextInputField(label = "Username", value = username) { username = it }

                Spacer(modifier = Modifier.height(16.dp))

                // Email Input
                TextInputField(label = "Email", value = email) { email = it }

                Spacer(modifier = Modifier.height(16.dp))

                // Phone Number Input
                TextInputField(label = "Phone Number", value = phoneNumber) { phoneNumber = it }

                Spacer(modifier = Modifier.height(24.dp))
                if (showToast){
                    Toast.makeText(context,toastMessage,Toast.LENGTH_SHORT)
                    showToast=false
                }
                // Save Changes Button
                Button(
                    onClick = { GlobalScope.launch(Dispatchers.IO) {
                        FireBaseAuthApi.updateUserData(
                            userName = username,
                            lastName = lastName,
                            firstName = firstName,
                            email = email,
                            phoneNumber = phoneNumber,
                            onSuccess = {
                                toastMessage= "Successfully Updated!"
                                showToast = true
                            },
                            onFail = {
                                toastMessage = it
                                showToast = true
                            }
                        )
                    } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = greenColor),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Save Changes",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Delete Account Text
                TextButton(
                    onClick = {

                    },

                    modifier = Modifier.fillMaxWidth(1f),
                    shape = RectangleShape
                ) {
                    Text(
                        text = "Delete Account",
                        color = Color.Black,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }



        }

    }
}

@Composable
fun TextInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}

