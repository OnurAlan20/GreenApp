package com.example.greenapp.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileSettingScreen(navController: NavHostController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("EmilyJ") }
    var email by remember { mutableStateOf("emily.j@example.com") }
    var phoneNumber by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    val greenColor = Color(0xFF00C853)
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val file = getFileFromUri(context as Activity, it)
            val fileName = "profile_picture.jpg"
            uploadImageToImgBB(
                file,
                fileName,
                onSuccess = { url ->
                    imageUrl = url
                    toastMessage = "Image uploaded successfully!"
                    showToast = true
                },
                onFailure = { error ->
                    toastMessage = error
                    showToast = true
                }
            )
        }
    }
    GlobalScope.launch(Dispatchers.IO) {
        val data = FireBaseAuthApi.getUserData { }
        username = data?.get("userName").toString()
        email = data?.get("email").toString()
        phoneNumber = data?.get("phoneNumber").toString()
        firstName = data?.get("firstName").toString()
        lastName = data?.get("lastName").toString()
        imageUrl = data?.get("userImage").toString()
    }
    Scaffold(topBar = {
        SettingsTopAppBar(
            title = "Profile Settings",
            pos = 0.25f,
            navController = navController
        )
    }, bottomBar = { SettingsBottomAppBar(navController) }) { it ->
        Surface(modifier = Modifier
            .fillMaxSize(1f)
            .padding(it)
            .background(Color(0xFFE8E8E8))) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // Profile Image
                AsyncImage(
                    model = imageUrl, // Replace with actual URL
                    contentDescription = "Profile Picture",
                    placeholder = painterResource(id = R.drawable.place_holder_pp), // Add a placeholder resource
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clickable {
                            imagePickerLauncher.launch("image/*")
                        }
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
                    text = "@" + username,
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
                if (showToast) {
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                    showToast = false
                }
                // Save Changes Button
                Button(
                    onClick = {
                        GlobalScope.launch(Dispatchers.IO) {
                            FireBaseAuthApi.updateUserData(
                                userName = username,
                                lastName = lastName,
                                firstName = firstName,
                                email = email,
                                phoneNumber = phoneNumber,
                                imageUrl = imageUrl,
                                onSuccess = {
                                    toastMessage = "Successfully Updated!"
                                    showToast = true
                                },
                                onFail = {
                                    toastMessage = it
                                    showToast = true
                                }
                            )
                        }
                    },
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

fun uploadImageToImgBB(
    file: File,
    fileName: String,
    onSuccess: (String) -> Unit,
    onFailure: (String) -> Unit
) {
    val url = "https://api.imgbb.com/1/upload?key=d6ac6a2edb0316131b0373c7bca3d277"

    val requestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart(
            "image",
            fileName,
            RequestBody.create("image/*".toMediaTypeOrNull(), file)
        )
        .build()

    val request = Request.Builder()
        .url(url)
        .post(requestBody)
        .build()

    val client = OkHttpClient()
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onFailure(e.message ?: "Unknown error")
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                val imageUrl = extractImageUrlFromResponse(responseBody)
                onSuccess(imageUrl)
            } else {
                onFailure("Error: ${response.message}")
            }
        }
    })
}

private fun extractImageUrlFromResponse(responseBody: String?): String {
    val jsonObject = JSONObject(responseBody ?: "")
    return jsonObject.getJSONObject("data").getString("url")
}

private fun getFileFromUri(activity: Activity, uri: Uri): File {
    val inputStream: InputStream? = activity.contentResolver.openInputStream(uri)
    val file = File(activity.cacheDir, "temp_image.jpg")
    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()
    return file
}
