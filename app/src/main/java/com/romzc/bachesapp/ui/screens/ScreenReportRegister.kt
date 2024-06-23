package com.romzc.bachesapp.ui.composables

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.romzc.bachesapp.DataStoreClass
import com.romzc.bachesapp.data.entities.PotholeEntity
import com.romzc.bachesapp.viewmodel.PotholeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import com.romzc.bachesapp.viewmodel.SeverityViewModel

/**
 * Datos de un bache:
 *  - Foto del lugar
 *  - Titulo
 *  - Descripcion
 *  - Latitud y longitud
 *  - Fecha
 */

@Composable
fun ScreenReportRegister(
    navController: NavController,
    userId: Int,
    saveData: () -> Unit,
    isCameraGranted: Boolean,

) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val dataStore = DataStoreClass(LocalContext.current)
    val savedId = dataStore.getId.collectAsState(initial = -1)

    val title = remember { mutableStateOf("")}
    val description = remember { mutableStateOf("") }
    val isCameraPermissionGranted = remember { mutableStateOf(isCameraGranted) }
    val capturedImage = remember { mutableStateOf<Bitmap?>(null) }
    val imageUri = remember { mutableStateOf("") }
    val severity = remember { TextFieldState() }
    val showError = remember { mutableStateOf(false) }

    val mPotholeViewModel: PotholeViewModel = ViewModelProvider(LocalContext.current as ViewModelStoreOwner).get(PotholeViewModel::class.java)

    CustomNavBar(navController = navController, title = "Registrar Bache")

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        ImageWithDefault(capturedImage.value, contentDescription = "Text" )

        if (isCameraPermissionGranted.value) {
            CameraButton(onImageCaptured = {
                capturedImage.value = it

            })
        }
        else {
            Button(
                onClick = { Log.i("APP", "Acceso a camara") },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Permitir acceso a la cámara")
            }
        }

        CommonTextField(
            title = "Titulo",
            placeholder = "Bache en...",
            text = title.value,
            onValueChange = { title.value = it }
        )

        CommonTextField(
            title = "Descripción",
            placeholder = "En la av...",
            text = description.value,
            onValueChange = { description.value = it }
        )

        dropDownMenu(severity)

        if (showError.value) {
            Text(
                text = "Por favor, complete los campos",
                textAlign = TextAlign.Center,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        Row( Modifier.padding(top = 10.dp)) {
            CommonButton(text = "Guardar", onClick = {
                val bitmapAux = capturedImage.value
                if(validateForm(title.value, description.value, bitmapAux)) {
                    // Ojo Corregir.
                    if (bitmapAux != null) {
                        storeBitMap(
                            bitmapAux,
                            context,
                            lifecycleOwner.lifecycleScope,
                            imageUri
                        )
                    }
                    Log.i("APP", "${title.value} ${description.value} ${imageUri.value}")
                    val newPothole = PotholeEntity(
                        potDesc = description.value,
                        potImg = imageUri.value,
                        potSev = severity.id,
                        potUser = savedId.value!!
                    )
                    mPotholeViewModel.addPothole(newPothole)
                    navController.popBackStack()

                } else showError.value = true
            })
            Spacer(modifier = Modifier.width(20.dp))
            CommonButton(text = "Cancelar", onClick = { navController.popBackStack() })
        }
    }
}




fun storeBitMap(bitMap: Bitmap, context: Context, lifeCycleScope: LifecycleCoroutineScope, mutableState: MutableState<String>) {
    // Convertimos la imagen.
    val bitmapNative = bitMap.asImageBitmap().asAndroidBitmap()

    // Obtenemos la direccion URI de la imagen que deseamos almacenar
    val photoUri = createPhotoName()
    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), photoUri)
    mutableState.value = file.absolutePath
    // Iniciamos una corrutna para almacenar el bitmap en segundo plano,
    lifeCycleScope.launch(Dispatchers.IO) {
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(file)
            // Comprime el bitmap en formato JPEG y escribe los datos en el archivo
            bitmapNative.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            // Notificamos a la galería de media para que escanee el nuevo archivo
            MediaScannerConnection.scanFile(context, arrayOf(file.toString()), null, null)
        } catch (e: Exception) {
            // Manejar errores o excepciones aquí
            Log.e("APP", "$e, esto paso Aqui")
        } finally {
            outputStream?.close()
        }
    }
}

/**
 * Retorna verdadero si los datos son correctos caso contrario false.
 */
fun validateForm(title: String, description: String, bitmap: Bitmap?): Boolean {
    return title.isNotEmpty() && description.isNotEmpty() && bitmap != null
}

fun createPhotoName(): String {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    return "IMG_$timeStamp.jpg"
}

class TextFieldState(){
    var text: String by mutableStateOf("")
    var id: Int by mutableStateOf(-1)
}

@Composable
fun dropDownMenu(svrty : TextFieldState = remember { TextFieldState() }) {
    lateinit var mSeverityViewModel: SeverityViewModel
    mSeverityViewModel= ViewModelProvider(LocalContext.current as ViewModelStoreOwner).get(SeverityViewModel::class.java)


    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Grave", "Medio", "Leve")


    var textfieldSize by remember { mutableStateOf(Size.Zero)}
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column () {
        OutlinedTextField(
            value = svrty.text,
            readOnly = true,
            onValueChange = { svrty.text = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textfieldSize = coordinates.size.toSize()
                },
            label = {Text("Blood type")},
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "contentDescription",
                    Modifier.clickable { expanded = !expanded }
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textfieldSize.width.toDp()})
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    svrty.text = label
                    expanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }

}
