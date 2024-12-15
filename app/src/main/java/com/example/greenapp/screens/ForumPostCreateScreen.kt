package com.example.greenapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ForumPostCreateScreen(navController: NavHostController) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var content by remember { mutableStateOf(TextFieldValue("")) }
    var category by remember { mutableStateOf("") }
    val categories = listOf("Recycling", "Sustainability", "Composting", "Energy Saving")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ekran Başlığı
        Text(
            text = "Create a New Post",
            fontSize = 20.sp,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Title Alanı
        Text(text = "Title", style = MaterialTheme.typography.labelLarge, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            placeholder = { Text("Enter a title") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Content Alanı
        Text(text = "Content", style = MaterialTheme.typography.labelLarge, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            placeholder = { Text("Share your best recycling tips here.") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(bottom = 16.dp)
        )

        // Add Image Butonu
        Button(
            onClick = { /* Resim ekleme işlemi burada yapılacak */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "+ Add Image", color = Color.Black)
        }

        // Category Seçimi
        Text(text = "Category", style = MaterialTheme.typography.labelLarge, modifier = Modifier.fillMaxWidth())
        var expanded by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = if (category.isEmpty()) "Select a category" else category)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                categories.forEach { cat ->
                    DropdownMenuItem(
                        text = { Text(cat) },
                        onClick = {
                            category = cat
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Submit Post Butonu
        Button(
            onClick = {
                // Submit işlemi burada yapılacak
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
