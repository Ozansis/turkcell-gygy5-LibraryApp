package com.turkcell.libraryapp.ui.navigation

// sayfa route tanımlaro
sealed class Screen(val route: String) {

    object Login : Screen("login")
    object Register : Screen("regsiter")

    object HomePage : Screen("homepage")

    object Splash : Screen("splash")

    object Profile : Screen("profil")

    object History : Screen("history")

    object BookDetail : Screen("book_detail/{bookId}") {
        fun createRoute(bookId: String) = "book_detail/$bookId"
    }
}