package com.romzc.bachesapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.romzc.bachesapp.data.User
import com.romzc.bachesapp.data.entities.PotholeEntity
import com.romzc.bachesapp.navigation.Routes
import com.romzc.bachesapp.ui.composables.ScreenReportRegister
import com.romzc.bachesapp.ui.screens.ScreenReportList
import com.romzc.bachesapp.ui.screens.ScreenUserLogin
import com.romzc.bachesapp.ui.screens.ScreenUserRegister
import com.romzc.bachesapp.utils.CustomLifecycleOwner
import com.romzc.bachesapp.ui.composables.*
import com.romzc.bachesapp.ui.screens.ScreenReportDetail

class MainActivity : ComponentActivity() {

    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)
    private var saveFilePermission : MutableState<Boolean> = mutableStateOf(false)

    private val requestStoragePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted -> if (isGranted) saveFilePermission.value = true  }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted -> if (isGranted) shouldShowCamera.value = true }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val dataStore = DataStoreClass(LocalContext.current)
            val savedId = dataStore.getId.collectAsState(initial = -1)

            CompositionLocalProvider(LocalLifecycleOwner provides this) {
                NavHost(
                    navController = navController,
                    startDestination = determineStartDestination(savedId.value)
                ) {
                    composable(Routes.ReportList.route) {
                        ScreenReportList(navController = navController)
                    }
                    composable(Routes.ReportRegister.route) {
                        ScreenReportRegister(
                            navController = navController,
                            userId = 1,
                            saveData = { /*TODO*/ },
                            isCameraGranted = shouldShowCamera.value
                        )
                    }

                    composable(route = "${Routes.ReportDetail.route}/{id}") { backStackEntry  ->
                        val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                        ScreenReportDetail(navController, id ?: -1)
                    }

                    composable(Routes.UserLogin.route) {
                        ScreenUserLogin(naveController = navController)
                    }

                    composable(Routes.UserRegister.route) {
                        ScreenUserRegister(naveController = navController)
                    }
                }
            }
        }
        requestCameraPermission()
        requestStoragePermission()
    }

    private fun determineStartDestination(savedId: Int?): String {
        return if (savedId != -1) {
            Routes.ReportList.route
        } else {
            Routes.UserLogin.route
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("APP", "Permission previously granted")
                shouldShowCamera.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> Log.i("APP", "Show camera permission dialog")

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
    private fun requestStoragePermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("APP", "Permission previously granted to write")
                saveFilePermission.value = true
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> Log.i("APP", "Show camera permission dialog")
            else -> requestStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
}