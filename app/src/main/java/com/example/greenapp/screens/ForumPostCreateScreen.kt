package com.example.greenapp.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.greenapp.R
import com.example.greenapp.components.EcoForumTopAppBar
import com.example.greenapp.model.PostsData
import com.example.greenapp.model.UserModel
import com.example.greenapp.network.FireBaseAuthApi
import com.example.greenapp.network.FireBaseForumPostApi
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

@Composable
fun ForumPostCreateScreen(navController: NavHostController) {
    var content by remember { mutableStateOf(TextFieldValue("")) }
    val firebaseForumPostApi = FireBaseForumPostApi()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var selectedImageUrl by remember { mutableStateOf("") }
    var userImage by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }

    val selectImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            val file = firebaseForumPostApi.uriToFile(context, uri)
            if (file != null) {
                coroutineScope.launch {
                    firebaseForumPostApi.uploadImageToImgBB(
                        file = file,
                        fileName = UUID.randomUUID().toString(),
                        onSuccess = { url ->
                            selectedImageUrl = url
                            println(selectedImageUrl)
                        },
                        onFailure = { error ->
                            println(error)
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val data = FireBaseAuthApi.getUserData()
            userName = data?.get("userName").toString()
            userImage = data?.get("userImage").toString()
        }
    }

    Scaffold(
        topBar = {
            EcoForumTopAppBar(
                iconId = R.drawable.left_arrow,
                title = "Create a New Post"
            ) {
                navController.navigate("forum_posts")
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color(0xfff6f8f9))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Content",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    placeholder = { Text("Share your best recycling tips here.") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .padding(bottom = 16.dp)
                )

                Button(
                    onClick = {
                        selectImageLauncher.launch("image/*")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_button),
                            contentDescription = "Add Image",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Add Image", color = Color.Black)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (selectedImageUrl != ""){
                                val newPost = PostsData(
                                    userImage = userImage,
                                    userName = userName,
                                    text = content.text,
                                    image = selectedImageUrl,
                                    likeCount = 0,
                                    likedUsers = listOf(),
                                    creationDate = Date()
                                )
                                firebaseForumPostApi.addPost(
                                    post = newPost,
                                    onSuccess = {
                                        println("Post başarıyla eklendi!")
                                        navController.navigate("forum_posts")
                                    },
                                    onFailure = { e ->
                                        println("Post eklenirken hata oluştu: ${e.message}")
                                    }
                                )
                            }else{
                                Toast.makeText(context,"Lütfen resim seçin",Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34C759)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Submit Post", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}
