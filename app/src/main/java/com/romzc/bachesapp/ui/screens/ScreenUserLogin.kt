package com.romzc.bachesapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.romzc.bachesapp.DataStoreClass
import com.romzc.bachesapp.R
import com.romzc.bachesapp.navigation.Routes
// import com.example.danp_examen.viewmodel.UserViewModel
import com.romzc.bachesapp.ui.theme.DarkOrange
import com.romzc.bachesapp.ui.theme.LGray
import com.romzc.bachesapp.ui.theme.LightOrange
import com.romzc.bachesapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch
@Composable
fun ScreenUserLogin(naveController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passView by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    val dataStore = DataStoreClass(LocalContext.current)
    val mUserViewModel: UserViewModel = ViewModelProvider(LocalContext.current as ViewModelStoreOwner).get(UserViewModel::class.java)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_map_peru),
            contentDescription = "Fondo de pantalla",
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            colorFilter = ColorFilter.tint(LGray)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_road),
                    contentDescription = "Road Icon",
                    tint = DarkOrange,
                    modifier = Modifier.size(70.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        text = "APLICATIVO",
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        color = DarkOrange,
                        fontWeight = FontWeight.Bold,
                        text = "MANTENIMIENTO",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),

            ) {
            Text(
                text = "INICIO DE SESION",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(height = 20.dp))

            OutlinedTextField(
                value = email,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = DarkOrange,
                    backgroundColor = Color.White,
                    cursorColor = DarkOrange,
                    focusedLabelColor = DarkOrange,
                ),

                onValueChange = { email = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = { Text(text = "Correo electronico") },
                modifier = Modifier.fillMaxWidth(),

                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon"
                    )
                }
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = DarkOrange,
                    backgroundColor = Color.White,
                    cursorColor = DarkOrange,
                    focusedLabelColor = DarkOrange,
                ),

                label = { Text(text = "Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = if (passView) R.drawable.ic_password_off else R.drawable.ic_password_eye),
                        contentDescription = "Eye Icon",
                        modifier = Modifier.clickable { passView = !passView }
                    )
                },
                visualTransformation = if (passView) VisualTransformation.None
                else PasswordVisualTransformation(),
            )
            Spacer(modifier = Modifier.height(height = 30.dp))


            Button(
                onClick = {
                    mUserViewModel.viewModelScope.launch {
                        val credentialsValid = mUserViewModel.checkUser(email, password)
                        if (credentialsValid) {
                            val id = mUserViewModel.getUserId(email, password)?: -1
                            naveController.navigate(Routes.ReportList.route)
                            dataStore.saveId(id)
                        } else{
                            showError = true
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                ),
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                modifier = Modifier
                    .align(Alignment.End)
                    .height(50.dp)
                    .width(180.dp)
                    .background(
                        brush = Brush.horizontalGradient(listOf(LightOrange, DarkOrange)),
                        shape = RoundedCornerShape(16.dp)
                    )

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "INGRESAR",
                        fontSize = 15.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                    Icon(
                        modifier = Modifier
                            .padding(start = 10.dp),
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Arrow Icon",
                        tint = Color.White,

                        )
                }
            }

        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showError) {
                Text(
                    text = "Por favor, revise sus credenciales",
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 100.dp)
                )
            }
            Row() {
                Text(
                    text = "¿No tienes una cuenta?",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp,
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp)
                        .clickable {
                            naveController.navigate(Routes.UserRegister.route)
                        },

                    color = DarkOrange,
                    fontWeight = FontWeight.Bold,
                    text = "Registrate",
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp,
                )
            }
        }
    }
}