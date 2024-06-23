package com.romzc.bachesapp.ui.composables

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.runtime.Composable

@Composable
fun CameraButton (
    onImageCaptured: (Bitmap) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()){ bitmap ->
        onImageCaptured(bitmap)
    }

    CommonButton( onClick = {launcher.launch()}, text = "Tomar foto")

}