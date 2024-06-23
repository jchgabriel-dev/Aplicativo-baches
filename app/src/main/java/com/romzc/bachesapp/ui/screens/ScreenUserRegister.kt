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
import androidx.compose.material.icons.filled.Person
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
import com.romzc.bachesapp.R
import com.romzc.bachesapp.data.entities.UserEntity
import com.romzc.bachesapp.navigation.Routes
//import com.example.danp_examen.entities.UserEntity
//import com.example.danp_examen.viewmodel.UserViewModel
import com.romzc.bachesapp.ui.theme.DarkOrange
import com.romzc.bachesapp.ui.theme.LGray
import com.romzc.bachesapp.ui.theme.LightOrange
import com.romzc.bachesapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun ScreenUserRegister( naveController: NavController ) {

    lateinit var mUserViewModel: UserViewModel

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confPass by remember { mutableStateOf("") }
    var passView by remember { mutableStateOf(false) }
    var confView by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    mUserViewModel = ViewModelProvider(LocalContext.current as ViewModelStoreOwner).get(UserViewModel::class.java)

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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),

            ) {
            Text(
                text = "CREACION DE CUENTA",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(height = 20.dp))

            OutlinedTextField(
                value = username,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = DarkOrange,
                    backgroundColor = Color.White,
                    cursorColor = DarkOrange,
                    focusedLabelColor = DarkOrange,
                ),

                onValueChange = { username = it },
                label = { Text(text = "Nombre de usuario") },
                modifier = Modifier.fillMaxWidth(),

                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Person Icon"
                    )
                }
            )

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

            OutlinedTextField(
                value = confPass,
                onValueChange = { confPass = it },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = DarkOrange,
                    backgroundColor = Color.White,
                    cursorColor = DarkOrange,
                    focusedLabelColor = DarkOrange,
                ),

                label = { Text(text = "Confirmar contraseña") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = if (confView) R.drawable.ic_password_off else R.drawable.ic_password_eye),
                        contentDescription = "Eye Icon",
                        modifier = Modifier.clickable { confView = !confView }
                    )
                },
                visualTransformation = if (confView) VisualTransformation.None
                else PasswordVisualTransformation(),
            )


            Spacer(modifier = Modifier.height(height = 30.dp))


            Button(
                onClick = {
                    if (checkValues(username, password, confPass, email)) {
                        val user = UserEntity(
                            userName = username,
                            userPass = password,
                            userEmai = email
                        )
                        mUserViewModel.addUser(user)
                        mUserViewModel.viewModelScope.launch {
                            val id = mUserViewModel.getUserId(email, password)
                            naveController.navigate(Routes.UserLogin.route)
                        }

                    } else {
                        showError = true
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
                        text = "CREAR",
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
                    text = "Por favor, complete todos los campos correctamente",
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 30.dp, start = 30.dp, end = 30.dp)
                )
            }

            Row() {

                Text(
                    text = "¿Ya tienes una cuenta?",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp,
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp)
                        .clickable {
                            naveController.navigate(Routes.UserLogin.route)
                        },

                    color = DarkOrange,
                    fontWeight = FontWeight.Bold,
                    text = "Ingresa",
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp,
                )
            }
        }
    }
}

fun checkValues(username: String, password: String,  confPass: String, email: String): Boolean {
    if (username.isEmpty() || password.isEmpty() || confPass.isEmpty() || email.isEmpty()) {
        return false
    }
    if (password != confPass) {
        return false
    }
    return true
}
