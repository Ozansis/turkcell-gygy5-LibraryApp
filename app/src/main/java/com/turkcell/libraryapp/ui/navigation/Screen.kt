package com.turkcell.libraryapp.ui.navigation
// sayfa route tanımlaro
sealed class Screen(val route : String) {

    object Login : Screen("login")
    object Register : Screen("regsiter")

    object HomePage : Screen("homepage")
}