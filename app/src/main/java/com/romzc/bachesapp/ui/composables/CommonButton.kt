package com.romzc.bachesapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun CommonButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(100.dp, 41.dp)
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(16.dp),
                color = Color.Blue,
            )
            .background(Color.Blue, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight(600)
        )
    }
}