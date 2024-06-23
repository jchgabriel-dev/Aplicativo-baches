package com.romzc.bachesapp.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter
import androidx.compose.ui.unit.dp
import android.graphics.Bitmap
import android.graphics.BitmapFactory

@Composable
fun ImageComponent(imagePath: String) {
    Image(
        painter = rememberImagePainter(imagePath),
        contentDescription = null,
        modifier = Modifier.size(100.dp)
    )
}

fun getBitmapFromPath(imagePath: String): Bitmap? {
    return BitmapFactory.decodeFile(imagePath)
}