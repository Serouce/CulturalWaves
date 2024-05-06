package com.example.android.culturalwaves.ui.components



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material.icons.filled.FavoriteBorder
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.SubcomposeAsyncImage
//import com.example.android.culturalwaves.R

//@Composable
//fun CardTemplate(
//    imageUrl: String,
//    title: String,
//    artist: String,
//    isFavorite: Boolean = false,
//    onClick: () -> Unit,
//    onFavoriteClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = Modifier
//            .width(330.dp)
//            .height(300.dp)
//            .background(Color(0xFFF5F5DC).copy(alpha = 0.8f))
//            .clickable { onClick },
//        elevation = CardDefaults.cardElevation(10.dp)
//
//    ) {
//        Column {
//            SubcomposeAsyncImage(
//                model = imageUrl,
//                contentDescription = title,
//                modifier = Modifier
//                    .width(308.dp)
//                    .height(247.dp),
//                loading = {
//                    CircularProgressIndicator()
//                },
//                contentScale = ContentScale.Crop
//                )
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(4.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column(
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                    Text(text = title, fontSize = 16.sp)
//                    Text(text = artist, fontSize = 14.sp)
//                }
//                Icon(
//                    imageVector = if (isFavorite) Icons.Filled.Favorite
//                    else Icons.Filled.FavoriteBorder,
//                    contentDescription = stringResource(R.string.add_to_favorites),
//                    modifier = Modifier
//                        .clickable { onFavoriteClick }
//                )
//            }
//
//        }
//
//    }
//}
//
//


@Composable
fun CardTemplate(
    imageUrl: String,
    title: String,
    artist: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onCardClick: () -> Unit // Функция для обработки клика по карточке
) {
    Card(
        modifier = Modifier
            .size(width = 330.dp, height = 300.dp)
            .clickable(onClick = onCardClick) // Добавляем кликабельность для карточки
            .background(Color(0xFFF5F5DC).copy(alpha = 0.8f), RoundedCornerShape(10.dp))
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ArtworkImage(
                imageUrl = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .height(247.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 5.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = artist,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Избранное",
                        modifier = Modifier.size(width = 20.dp, height = 15.63.dp)
                    )
                }
            }
        }
    }
}

