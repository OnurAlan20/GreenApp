package com.example.greenapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.sharp.KeyboardArrowLeft
import androidx.compose.material.icons.sharp.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.greenapp.R
import com.example.greenapp.components.BottomAppBar
import com.example.greenapps.screens.RegisterScreen

@Composable
fun SettingsScreen(navHostController: NavHostController){
    val navController = rememberNavController()
    Scaffold (topBar = { SettingsTopAppBar(title = "Settings") }, bottomBar = { SettingsBottomAppBar(navController) }){
        it->
        Surface(modifier = Modifier.fillMaxSize(1f).padding(it)) {
            SettingsMenu {
                navHostController.navigate("profile_setting")
            }
        }

    }
}
@Composable
fun SettingsMenu(onDetailClick:()->Unit){
    Surface(modifier = Modifier.fillMaxSize(1f), color = Color(0xFFE8E8E8)) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Settings Interface Section
            SettingsSection(
                title = "Settings Interface",
                options = listOf(
                    SettingOption("Profile Settings", { Icon(imageVector = Icons.Filled.Person, contentDescription = null) }, onClick = {
                        onDetailClick()
                    }),
                    SettingOption("Notification Settings", { Icon(imageVector = Icons.Filled.Notifications, contentDescription = null) })
                )
            )

            // Privacy & Theme Section
            SettingsSection(
                title = "Privacy & Theme",
                options = listOf(
                    SettingOption("Privacy Settings", { Icon(imageVector = Icons.Filled.Lock, contentDescription = null) }),
                    SettingOption("App Theme", { Icon(painter = painterResource(R.drawable.bright_icon), contentDescription = null, modifier = Modifier.size(30.dp)) }, subtitle = "Light"),
                    SettingOption("Language Settings", { Icon(painter = painterResource(R.drawable.translation), contentDescription = null, modifier = Modifier.size(25.dp)) }, subtitle = "English")
                )
            )

            // Account Section
            SettingsSection(
                title = "Account",
                options = listOf(
                    SettingOption("Account Settings", { Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = null) }),
                    SettingOption("Log Out",
                        { Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = null) })
                )
            )
        }
    }
}

@Composable
fun SettingsBottomAppBar(navController: NavHostController){
    BottomAppBar(
        onChatClick = { navController.navigate("forum_topics")},
        onForumClick = { navController.navigate("forum_posts") },
        onSettingsClick = { navController.navigate("settings_menu") }
    )
}


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopAppBar(onMenuClick: () -> Unit={},title: String="Settings",pos:Float=0.35f) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        title = {
            Row(

                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(top = 5.dp, bottom = 7.dp)
            ) {
                // Menü İkonu
                IconButton(onClick = onMenuClick,modifier = Modifier.background(color = Color.Transparent).fillMaxWidth(pos)) {
                    Row(Modifier.fillMaxWidth(1f)) {
                        Icon(
                            imageVector = Icons.Sharp.KeyboardArrowLeft, // Menü ikonu
                            contentDescription = "Menu"
                        )
                    }
                }
                // Başlık
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,

                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )

            }
        },
    )
}

@Composable
fun SettingsSection(title: String, options: List<SettingOption>) {
    Column(modifier = Modifier.padding(bottom = 24.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 1.dp)
        )
        options.forEach { option ->
            SettingItem(option)
        }

    }
}

@Composable
fun SettingItem(option: SettingOption) {
    ListItem(
        headlineContent = {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(1f)) {
                Text(
                    text = option.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                Icon(imageVector = Icons.Sharp.KeyboardArrowRight,contentDescription = null)
            }

        },
        supportingContent = {
            option.subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        },
        leadingContent = {
            option.icon()
        },
        modifier = Modifier.padding(bottom = 3.dp).clickable {
            option.onClick()
        }
    )
}

data class SettingOption(
    val title: String,
    val icon: @Composable ()->Unit,
    val subtitle: String? = null,
    val onClick: ()->Unit={}
)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewProfileSettingScreen(){
    SettingsMenu {  }
}