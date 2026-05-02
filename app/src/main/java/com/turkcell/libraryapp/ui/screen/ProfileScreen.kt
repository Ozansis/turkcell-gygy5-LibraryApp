package com.turkcell.libraryapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.turkcell.libraryapp.data.model.BorrowStatus
import com.turkcell.libraryapp.ui.viewmodel.AuthViewModel
import com.turkcell.libraryapp.ui.viewmodel.BookViewModel
import com.turkcell.libraryapp.ui.viewmodel.BorrowViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateToHistory: () -> Unit,
    onNavigateToHome: () -> Unit,
    bookViewModel: BookViewModel,
    borrowViewModel: BorrowViewModel,
    authViewModel: AuthViewModel,
) {
    val profileState by authViewModel.profile.collectAsState()
    val books by bookViewModel.books.collectAsState()
    val borrows by borrowViewModel.borrows.collectAsState()

    LaunchedEffect(profileState?.userId) {
        profileState?.userId?.let { userId ->
            borrowViewModel.loadMyBorrows(userId)
        }
    }


    val borrowedBooksUi = borrowViewModel.getBorrowedBooksUiModels(books)
    val activeBorrows = borrowedBooksUi.filter { it.status == BorrowStatus.ACTIVE }
    val historyBorrows = borrowedBooksUi.filter { it.status != BorrowStatus.ACTIVE }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Profile",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A2C42)
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigateToHome() },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Discover") },
                    label = { Text("Discover", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigateToHistory() },
                    icon = { Icon(Icons.Default.History, contentDescription = "History") },
                    label = { Text("History", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },
                    label = { Text("Profile", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1A2C42),
                        selectedTextColor = Color(0xFF1A2C42),
                        indicatorColor = Color(0xFFFFEAD1)
                    )
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8F9FA)),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = profileState?.fullName ?: "Guest User",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1A2C42)
                        )
                        Text(
                            text = "Archivist Member",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }


            if (activeBorrows.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Book, null, tint = Color(0xFF1A2C42), modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Currently Borrowed", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A2C42))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                items(activeBorrows) { bookUi ->
                    BorrowedBookCard(
                        title = bookUi.title,
                        author = bookUi.author,
                        dueDateDescription = bookUi.dueDateDescription,
                        onRenewClick = { /* Renew Logic */ }
                    )
                }
                
                item { Spacer(modifier = Modifier.height(32.dp)) }
            }


            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.History, null, tint = Color(0xFF1A2C42), modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Borrowing History", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A2C42))
                }
                Spacer(modifier = Modifier.height(12.dp))
            }


            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (historyBorrows.isEmpty()) {
                            Text(
                                "No history found.",
                                color = Color.Gray,
                                modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
                            )
                        } else {
                            historyBorrows.take(3).forEachIndexed { index, bookUi ->
                                ProfileHistoryItem(
                                    title = bookUi.title,
                                    author = bookUi.author,
                                    date = bookUi.dueDateDescription // ViewModel'den dönen tarih açıklaması
                                )
                                if (index < historyBorrows.take(3).size - 1) {
                                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF1F3F5))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        TextButton(
                            onClick = { onNavigateToHistory() },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("View Full History →", color = Color(0xFF1A2C42), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileHistoryItem(title: String, author: String, date: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A2C42))
            Text(text = author, fontSize = 13.sp, color = Color.Gray)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = "Returned", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Text(text = date, fontSize = 13.sp, color = Color(0xFF1A2C42), fontWeight = FontWeight.SemiBold)
        }
    }
}

