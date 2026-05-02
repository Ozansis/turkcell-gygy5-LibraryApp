package com.turkcell.libraryapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turkcell.libraryapp.ui.screen.HomePageScreen
import com.turkcell.libraryapp.ui.screen.LoginScreen
import com.turkcell.libraryapp.ui.screen.ProfileScreen
import com.turkcell.libraryapp.ui.screen.RegisterScreen
import com.turkcell.libraryapp.ui.screen.SplashScreen
import com.turkcell.libraryapp.ui.viewmodel.AuthViewModel
import com.turkcell.libraryapp.ui.viewmodel.BookViewModel
import com.turkcell.libraryapp.ui.viewmodel.BorrowViewModel


import com.turkcell.libraryapp.ui.screen.BookDetailScreen
import com.turkcell.libraryapp.ui.screen.BorrowingHistoryScreen

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    val authViewModel: AuthViewModel = viewModel()
    val bookViewModel: BookViewModel = viewModel()
    val borrowViewModel: BorrowViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(
                authViewModel,
                onAuthenticated = { role ->
                    navController.navigate(Screen.HomePage.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onUnauthenticated = {
                    navController.navigate(Screen.Login.route)
                    {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = { role ->

                    navController.navigate(Screen.HomePage.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }

                },
                authViewModel
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                authViewModel
            )
        }

        composable(Screen.HomePage.route) {
            HomePageScreen(
                authViewModel,
                bookViewModel,
                borrowViewModel,
                navController,
                onNavigateToHome = { navController.navigate(Screen.HomePage.route) },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToHistory = { navController.navigate(Screen.History.route) },
                onNavigateToLogin = { navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.HomePage.route) { inclusive = true }
                }}
                )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.HomePage.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToHistory = { navController.navigate(Screen.History.route) },
                bookViewModel =bookViewModel,
                borrowViewModel = borrowViewModel,
                authViewModel = authViewModel
            )
        }

        composable(Screen.History.route) {
            BorrowingHistoryScreen(navController, borrowViewModel, bookViewModel, authViewModel)
        }

        composable(Screen.BookDetail.route) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
            BookDetailScreen(
                bookId = bookId,
                onBackClick = { navController.popBackStack() },
                bookViewModel = bookViewModel,
                authViewModel = authViewModel,
                borrowViewModel = borrowViewModel
            )
        }

    }


}