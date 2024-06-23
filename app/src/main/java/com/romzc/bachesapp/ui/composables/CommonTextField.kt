package com.romzc.bachesapp.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CommonTextField(
    title:String,
    placeholder: String,
    text: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange =  onValueChange,
        label = { Text(text = title) },
        placeholder = { Text(text = placeholder) },
        modifier = Modifier.padding(top = 10.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    )
}