package com.romzc.bachesapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.romzc.bachesapp.ui.composables.CustomNavBar

@Composable
fun ScreenReportEdit(navController: NavController) {
    CustomNavBar(navController = navController, title = "Editar Reporte")
}