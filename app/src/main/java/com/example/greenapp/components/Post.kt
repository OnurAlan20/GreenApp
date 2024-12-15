package com.example.greenapp.components

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.greenapp.R
import com.example.greenapp.model.PostsData
import com.example.greenapp.network.FireBaseForumPostApi
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Composable
fun Post(postData: PostsData, fireBaseForumPostApi: FireBaseForumPostApi, currentUserId: String) {
    var isLiked by remember { mutableStateOf(currentUserId in postData.likedUsers) }
    var likeCount by remember { mutableStateOf(postData.likeCount) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .clip(shape = RoundedCornerShape(10.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = postData.userImage,
                contentDescription = "User Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = postData.userName,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = postData.creationDate?.toInstant()?.atZone(ZoneId.systemDefault())
                        ?.toLocalDateTime()
                        ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Post Metni
        Text(
            text = postData.text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Post Görseli
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = postData.image,
                contentDescription = "Post Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Beğeni ve Paylaşım Alanı
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Paylaş Butonu
            Icon(
                painter = painterResource(id = R.drawable.share),
                contentDescription = "Share Icon",
                modifier = Modifier
                    .size(25.dp)
                    .padding(end = 8.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "${postData.text} - Shared from GreenApp!")
                        }
                        context.startActivity(Intent.createChooser(intent, "Share Post"))
                    }
            )

            // Beğeni Butonu
            Icon(
                painter = if (isLiked) {
                    painterResource(id = R.drawable.favourite_filled)
                } else {
                    painterResource(id = R.drawable.favourite)
                },
                contentDescription = if (isLiked) "Liked" else "Not Liked",
                tint = if (isLiked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        val newLikedStatus = !isLiked
                        fireBaseForumPostApi.updateLikeStatus(
                            postId = postData.id,
                            userId = currentUserId,
                            isLiked = newLikedStatus,
                            onSuccess = {
                                isLiked = newLikedStatus
                                likeCount = if (newLikedStatus) likeCount + 1 else likeCount - 1
                            },
                            onFailure = { e ->
                                Log.e("Post", "Beğeni durumu güncellenirken hata oluştu: ${e.message}")
                            }
                        )
                    }
            )

            Spacer(modifier = Modifier.width(4.dp))

            // Beğeni Sayısı
            Text(
                text = "$likeCount Likes",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 14.sp
            )
        }
    }
}




