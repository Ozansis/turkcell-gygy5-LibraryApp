package com.turkcell.libraryapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.turkcell.libraryapp.data.model.BorrowStatus
import com.turkcell.libraryapp.ui.navigation.Screen
import com.turkcell.libraryapp.ui.viewmodel.AuthViewModel
import com.turkcell.libraryapp.ui.viewmodel.BookViewModel
import com.turkcell.libraryapp.ui.viewmodel.BorrowViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorrowingHistoryScreen(
    navController: NavController,
    borrowViewModel: BorrowViewModel,
    bookViewModel: BookViewModel,
    authViewModel: AuthViewModel
) {
    val books by bookViewModel.books.collectAsState()
    val borrowedBooksUi = borrowViewModel.getBorrowedBooksUiModels(books)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Borrowing History",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A2C42)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.HomePage.route) },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Discover") },
                    label = { Text("Discover", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )

                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.History, contentDescription = "History") },
                    label = { Text("History", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1A2C42),
                        selectedTextColor = Color(0xFF1A2C42),
                        indicatorColor = Color(0xFFFFEAD1)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Profile.route) },
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },
                    label = { Text("Profile", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8F9FA))
                .padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                placeholder = { Text("Search your history...", fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF1F3F5),
                    focusedContainerColor = Color(0xFFF1F3F5),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color(0xFF1A2C42)
                )
            )

            if (borrowedBooksUi.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No history found.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(borrowedBooksUi) { bookUi ->
                        val isReturned = bookUi.status == BorrowStatus.RETURNED
                        val isOverdue = bookUi.status == BorrowStatus.OVERDUE
                        
                        HistoryCard(
                            title = bookUi.title,
                            author = bookUi.author,
                            borrowDate = "DATE UNKNOWN", // İhtiyaç halinde bu da ViewModel'den gelebilir
                            returnDate = bookUi.dueDateDescription,
                            status = bookUi.status.name.lowercase().replaceFirstChar { it.uppercase() },
                            statusColor = when {
                                isReturned -> Color(0xFFE8F5E9)
                                isOverdue -> Color(0xFFFFEBEE)
                                else -> Color(0xFFE3F2FD) // Active
                            },
                            statusTextColor = when {
                                isReturned -> Color(0xFF2E7D32)
                                isOverdue -> Color(0xFFC62828)
                                else -> Color(0xFF1565C0)
                            },
                            isOverdue = isOverdue
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryCard(
    title: String,
    author: String,
    borrowDate: String,
    returnDate: String,
    status: String,
    statusColor: Color,
    statusTextColor: Color,
    isOverdue: Boolean = false
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(width = 70.dp, height = 100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE9ECEF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Book,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color.LightGray
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A2C42)
                )
                Text(
                    text = author,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "STATUS INFO:",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray
                )
                Text(
                    text = returnDate,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isOverdue) Color(0xFFC62828) else Color.Gray
                )
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = statusColor
            ) {
                Text(
                    text = status,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusTextColor
                )
            }
        }
    }
}
