package com.example.greenapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Notifications
import androidx.compose.material.icons.sharp.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.greenapp.R
import com.example.greenapp.components.BottomAppBar
import com.example.greenapp.components.UserImagePP
import com.example.greenapp.model.AIMessageModel
import com.example.greenapp.network.AIService
import com.example.greenapp.network.FireBaseAuthApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AiScreen(navHostController: NavHostController){
    val messageList = remember {
        mutableStateListOf<AIMessageModel>(

        )
    }
    var userName by remember {
        mutableStateOf("")
    }
    var imageUrl by remember {
        mutableStateOf("")
    }

    GlobalScope.launch(Dispatchers.IO) {
        val data = FireBaseAuthApi.getUserData {  }
        userName= data?.get("userName").toString()
        imageUrl = data?.get("userImage").toString()
    }

    Scaffold(bottomBar = { BottomAppBar(navHostController) }, topBar = { AiScreenTopAppBar() }) {
        Surface(modifier = Modifier.padding(it)) {
            Column(modifier = Modifier.fillMaxSize(1f)) {
                ChatScreen(userImageUrl = imageUrl, userName = userName, messageList = messageList)
                SendMessage(imageUrl=imageUrl,messageList)
            }

        }
    }
}

@Composable
fun MessagePartUser(userImageUrl:String,userName: String,userAIMessageModel: AIMessageModel){
    Row (modifier = Modifier.fillMaxWidth(1f).padding(10.dp), verticalAlignment = Alignment.CenterVertically){
        UserImagePP(userImageUrl)
        Row {
            Spacer(modifier = Modifier.width(8.dp)) // Fotoğraf ile metin arasındaki boşluk
            // Metin ve kutu içeriği
            Column(modifier = Modifier.fillMaxWidth()) {
                // Kullanıcı İsmi
                Text(
                    text = userName,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                // Boş Kutulu Alan
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.CenterStart // Metni başlangıca hizalamak için
                ) {
                    Text(
                        text = userAIMessageModel.message,
                        color = Color.DarkGray, // Metin rengini daha belirgin bir gri yaparak kontrast sağladık
                        fontSize = 16.sp, // Yazı boyutunu büyüttük
                        fontWeight = FontWeight.Medium, // Yazıyı kalınlaştırdık
                        modifier = Modifier.padding(start = 12.dp) // Sol kenardan boşluk ekledik
                    )
                }
            }
        }
    }
}

@Composable
fun MessagePartAi(userAIMessageModel: AIMessageModel){
    Row (modifier = Modifier.fillMaxWidth(1f).padding(10.dp),
        verticalAlignment = Alignment.CenterVertically){
        Row {
             // Fotoğraf ile metin arasındaki boşluk
            // Metin ve kutu içeriği
            Column(modifier = Modifier.fillMaxWidth(0.86f)) {
                // Kullanıcı İsmi
                Text(
                    text = "Green Bot",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                // Boş Kutulu Alan
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 50.dp)
                        .background(Color(0xFF00C853), shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.CenterStart // Metni başlangıca hizalamak için
                ) {
                    Text(
                        text = userAIMessageModel.message,
                        color = Color.White, // Metin rengini beyaz yaparak kontrast sağladık
                        fontSize = 16.sp, // Yazı boyutunu büyüttük
                        fontWeight = FontWeight.Medium, // Yazıyı kalınlaştırdık
                        modifier = Modifier.padding(start = 12.dp) // Sol kenardan boşluk ekledik
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(painterResource(R.drawable.bot),
            tint = Color(0xFF00C853) ,contentDescription = "",
            modifier = Modifier.size(40.dp))
    }
}

@Composable
fun ChatScreen(messageList: MutableList<AIMessageModel>,userImageUrl:String,userName:String){
    Box(modifier = Modifier
        .fillMaxWidth(1f)
        .fillMaxHeight(0.9f)){
        LazyColumn(modifier = Modifier.fillMaxSize(1f)) {
            items(messageList){
                if (it.sender == "user"){
                    MessagePartUser(userImageUrl,userName,it)
                }
                else{
                    MessagePartAi(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMessage(imageUrl: String = "",messageList: MutableList<AIMessageModel>) {
    var message by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F1F1))
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            // Profil resmi
            UserImagePP(imageUrl)

            Spacer(modifier = Modifier.width(8.dp))

            // Mesaj metin alanı
            Box(
                modifier = Modifier
                    .weight(1f).fillMaxWidth(1f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White) // Mesaj alanı arka plan
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row (modifier = Modifier.fillMaxWidth(1f)){
                    TextField(
                        value = message,
                        onValueChange = { newMessage -> message = newMessage },
                        placeholder = { Text("Enter your message...", color = Color.Gray,) },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp).fillMaxWidth(1f)// Yüksekliği genişletiyoruz
                            .clip(RoundedCornerShape(24.dp)),
                        suffix = {
                            Icon(Icons.Sharp.Send, contentDescription = "", tint = Color.Gray,
                                modifier = Modifier.clickable {
                                    sendMessage(messageList = messageList, newMessage = message)
                                })
                        }
                    )
                }


            }

            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Preview
@Composable
fun AiScreenTopAppBar(){
    Surface(modifier = Modifier.fillMaxWidth(1f)) {
        Row (modifier = Modifier.fillMaxWidth(1f).padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
            Icon(painter = painterResource(R.drawable.leaf), contentDescription = "", modifier = Modifier.size(30.dp))
            Text("AI Environmental Chat", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Icon(Icons.Sharp.Notifications, contentDescription = null, modifier = Modifier.size(30.dp))
        }
    }
}

fun sendMessage(messageList:MutableList<AIMessageModel>,newMessage:String){
    println("DENEMEEEEE")
    val aiMessageModel = AIMessageModel("user",newMessage)
    messageList.add(aiMessageModel)
    GlobalScope.launch(Dispatchers.IO) {
        val ai_response = AIService.sendMessage(newMessage)
        println(ai_response)
        val aiResponseModel = AIMessageModel("ai",ai_response)
        messageList.add(aiResponseModel)
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewAiScreen(){
    AiScreen(navHostController = rememberNavController())
}