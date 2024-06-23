package com.romzc.bachesapp.navigation

sealed class Routes(val route: String) {
    object ReportList : Routes("reports_pothole_screen")
    object ReportEdit : Routes("edit_pothole_scree")
    object ReportRegister : Routes("register_pothole_screen")
    object ReportDetail: Routes("detail_pothole_screen")
    object UserLogin: Routes("login_user_screen")
    object UserRegister: Routes("register_user_screen")

}
