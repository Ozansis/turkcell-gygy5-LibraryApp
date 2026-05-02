package com.turkcell.libraryapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.turkcell.libraryapp.ui.viewmodel.AuthViewModel
import com.turkcell.libraryapp.ui.viewmodel.SessionState
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    authViewModel: AuthViewModel,
    onAuthenticated: (String) -> Unit,
    onUnauthenticated: () -> Unit
) {
    val sessionState by authViewModel.sessionState.collectAsState()

    LaunchedEffect(sessionState) {
        delay(200)
        when (val s = sessionState) {
            is SessionState.Authenticated -> onAuthenticated(s.role)
            SessionState.Unauthenticated -> onUnauthenticated()
            SessionState.Initializing -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo Alanı
            Surface(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                color = Color(0xFFE9ECEF)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Color(0xFF1A2C42)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Uygulama İsmi
            Text(
                text = "Archivist",
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF1A2C42),
                letterSpacing = 1.sp
            )

            // Slogan
            Text(
                text = "Your Digital Sanctuary",
                fontSize = 16.sp,
                color = Color.Gray,
                letterSpacing = 0.5.sp
            )
        }

        // Alt taraftaki yükleniyor yazısı
        Text(
            text = "Loading...",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp),
            fontSize = 14.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF8F9FA)) {
        Box(contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    modifier = Modifier.size(100.dp).clip(CircleShape),
                    color = Color(0xFFE9ECEF)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = Color(0xFF1A2C42)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text("Archivist", fontSize = 42.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1A2C42))
                Text("Your Digital Sanctuary", fontSize = 16.sp, color = Color.Gray)
            }
            Text(
                "Loading...",
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 48.dp),
                color = Color.LightGray
            )
        }
    }
}
