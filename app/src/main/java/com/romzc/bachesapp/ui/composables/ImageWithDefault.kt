package com.romzc.bachesapp.ui.composables

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.romzc.bachesapp.R

@Composable
fun ImageWithDefault(
    imageMap: Bitmap?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    Box(
        modifier = modifier.size(width = 240.dp, height = 240.dp),
        contentAlignment = Alignment.Center
    ) {
        if(imageMap != null) {
            Image(
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize().padding(bottom = 10.dp),
                bitmap = imageMap.asImageBitmap()
            )
        }
        else {
            val imageId = R.drawable.baseline_landscape_24
            Image(
                painter = painterResource(imageId),
                contentDescription = contentDescription,
                modifier = Modifier.width(140.dp).height(140.dp),
            )
        }
    }
}