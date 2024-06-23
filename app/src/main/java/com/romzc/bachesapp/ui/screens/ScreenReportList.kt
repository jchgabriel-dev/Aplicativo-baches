package com.romzc.bachesapp.ui.screens

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.romzc.bachesapp.DataStoreClass
import com.romzc.bachesapp.R
import com.romzc.bachesapp.data.entities.PotholeEntity
import com.romzc.bachesapp.data.entities.UserEntity
import com.romzc.bachesapp.data.entities.UserWithPotholes
import com.romzc.bachesapp.navigation.Routes
import com.romzc.bachesapp.ui.composables.CustomNavBar
import com.romzc.bachesapp.ui.composables.ImageComponent
import com.romzc.bachesapp.ui.composables.ImageWithDefault
import com.romzc.bachesapp.ui.composables.getBitmapFromPath
import com.romzc.bachesapp.viewmodel.PotholeViewModel
import com.romzc.bachesapp.viewmodel.UserViewModel

@Composable
fun ScreenReportList(navController: NavController){

    lateinit var mUserViewModel: UserViewModel
    mUserViewModel = ViewModelProvider(LocalContext.current as ViewModelStoreOwner).get(UserViewModel::class.java)

    lateinit var mPotholeViewModel: PotholeViewModel
    mPotholeViewModel= ViewModelProvider(LocalContext.current as ViewModelStoreOwner).get(PotholeViewModel::class.java)

    val dataStore = DataStoreClass(LocalContext.current)
    val savedId = dataStore.getId.collectAsState(initial = -1)

    var user = remember<UserEntity?> { null }
    var potholes = remember { mutableStateListOf<PotholeEntity>() }

    LaunchedEffect(Unit) {
        val userWithPotholesList: List<UserWithPotholes> = mUserViewModel.getUserWithPotholes(savedId.value!!)

        for (userWithPotholes in userWithPotholesList) {
            user = userWithPotholes.user
            potholes.addAll(userWithPotholes.potholes)
        }
    }

    Text(
        text = "Mis reportes",
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 60.dp, 10.dp, 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        for (pothole in potholes) {
            Log.d("test",pothole.potDesc.toString())
            item {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .border(
                            border = BorderStroke(2.dp, Color.LightGray)
                        )
                ) {ItemReport(navController, pothole, mPotholeViewModel, potholes)}
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = {navController.navigate(Routes.ReportRegister.route)},
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_add_24),
                contentDescription = "añadir"
            )
        }
    }
}

@Composable
fun ItemReport (navController: NavController, pothole: PotholeEntity, mPotholeViewModel: PotholeViewModel, potholes: SnapshotStateList<PotholeEntity>){
    val showDialog = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.clickable {
            navController.navigate("${Routes.ReportDetail.route}/${pothole.potId}")

        }) {
            Row (verticalAlignment = Alignment.CenterVertically){
                val bitmap: Bitmap? = getBitmapFromPath(pothole.potImg)
                Box(
                    modifier = Modifier.size(140.dp),
                ) {
                    ImageWithDefault(imageMap = bitmap, contentDescription = "image")
                }
                Column {
                    Text(
                        modifier = Modifier
                            .width(150.dp)
                            .padding(0.dp, 10.dp),
                        text = pothole.potDesc
                    )
                    Text(
                        modifier = Modifier
                            .width(150.dp)
                            .padding(0.dp, 10.dp),
                        text = pothole.potSev.toString()
                    )
                    Text(
                        modifier = Modifier
                            .width(150.dp)
                            .padding(0.dp, 10.dp),
                        text = pothole.potDate
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = {navController.navigate(Routes.ReportEdit.route)}
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit_24),
                    contentDescription = "editar",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(40.dp)
                )
            }
            IconButton(
                onClick = {
                    showDialog.value = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete_24),
                    contentDescription = "Borrar",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(45.dp)
                )
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Confirmar eliminación") },
            text = { Text(text = "¿Estás seguro de que quieres eliminar este pothole?") },
            confirmButton = {
                Button(
                    onClick = {
                        mPotholeViewModel.deletePothole(pothole)
                        potholes.remove(pothole)
                        showDialog.value = false

                    }
                ) {
                    Text(text = "Eliminar")
                }
            },

            dismissButton = {
                Button(
                    onClick = { showDialog.value = false }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}
