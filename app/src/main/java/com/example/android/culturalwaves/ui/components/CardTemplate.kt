package com.example.android.culturalwaves.ui.components



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.android.culturalwaves.ui.theme.CulturalWavesTheme
import com.example.android.culturalwaves.utils.StringUtils


//@Composable
//fun CardTemplate(
//    imageUrl: String,
//    title: String,
//    artist: String,  // Добавлен параметр artist
//    objectId: Int,
//    isFavorite: Boolean,
//    onFavoriteClick: () -> Unit,
//    onCardClick: (Int) -> Unit,
//    cardWidth: Dp = 330.dp,
//    cardHeight: Dp = 300.dp
//) {
//    val displayedTitle = remember(title) { StringUtils.truncateString(title, 40) }
//    val displayedArtist = remember(artist) { StringUtils.truncateString(artist, 25) }  // Используем artist
//
//    Card(
//        modifier = Modifier
//            .size(width = cardWidth, height = cardHeight)
//            .clickable { onCardClick(objectId) }
//            .padding(10.dp),
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
//    ) {
//        Box(modifier = Modifier.fillMaxSize()) {
//            ArtworkImage(
//                imageUrl = imageUrl,
//                contentDescription = title,
//                modifier = Modifier.fillMaxSize()
//            )
//            Column(
//                modifier = Modifier
//                    .align(Alignment.BottomStart)
//                    .background(
//                        brush = Brush.verticalGradient(
//                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
//                            startY = 50f
//                        )
//                    )
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            ) {
//                Text(
//                    text = displayedTitle,
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = Color.White,
//                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
//                )
//                Text(
//                    text = displayedArtist,  // Используем displayedArtist
//                    style = MaterialTheme.typography.bodySmall,
//                    color = Color.White.copy(alpha = 0.7f),
//                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
//                )
//            }
//            IconButton(
//                onClick = onFavoriteClick,
//                modifier = Modifier
//                    .align(Alignment.TopEnd)
//                    .size(48.dp)
//                    .padding(8.dp)
//            ) {
//                Icon(
//                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
//                    contentDescription = "Избранное",
//                    tint = if (isFavorite) Color.Red else Color.White
//                )
//            }
//        }
//    }
//}

@Composable
fun CardTemplate(
    imageUrl: String,
    title: String,
    artist: String,
    objectId: Int,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onCardClick: (Int) -> Unit,
    onError: () -> Unit, // Добавлен параметр onError
    cardWidth: Dp = 330.dp,
    cardHeight: Dp = 300.dp
) {
    val displayedTitle = remember(title) { StringUtils.truncateString(title, 40) }
    val displayedArtist = remember(artist) { StringUtils.truncateString(artist, 25) }

    Card(
        modifier = Modifier
            .size(width = cardWidth, height = cardHeight)
            .clickable { onCardClick(objectId) }
            .padding(10.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            ArtworkImage(
                imageUrl = imageUrl,
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                onError = onError // Передача onError в ArtworkImage
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                            startY = 50f
                        )
                    )
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = displayedTitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
                )
                Text(
                    text = displayedArtist,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                )
            }
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(36.dp) // Уменьшение размера иконки
                    .padding(8.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.4f), // Полупрозрачный фон
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Избранное",
                    tint = if (isFavorite) Color.Red else Color.White
                )
            }
        }
    }
}



