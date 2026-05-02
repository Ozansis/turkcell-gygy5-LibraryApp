package com.turkcell.libraryapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turkcell.libraryapp.data.model.Book
import com.turkcell.libraryapp.ui.viewmodel.AuthViewModel
import com.turkcell.libraryapp.ui.viewmodel.BookViewModel
import com.turkcell.libraryapp.ui.viewmodel.BorrowViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    bookId: String,
    onBackClick: () -> Unit,
    bookViewModel: BookViewModel,
    authViewModel: AuthViewModel,
    borrowViewModel: BorrowViewModel
) {
    val selectedBook by bookViewModel.selectedBook.collectAsState()
    val isLoading by bookViewModel.isLoading.collectAsState()
    val profileState by authViewModel.profile.collectAsState()

    LaunchedEffect(bookId) {
        bookViewModel.getBookById(bookId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF1A2C42))
            }
        } else {
            selectedBook?.let { book ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(Color(0xFFF8F9FA))
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .aspectRatio(0.7f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFE9ECEF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Book,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = Color.LightGray
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = book.title,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A2C42),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "by ${book.author}",
                        fontSize = 18.sp,
                        color = Color.Gray,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Info Item: Pages
                        Column {
                            Text(
                                text = "PAGES",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.LightGray
                            )
                            Text(
                                text = "${book.pageCount}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A2C42)
                            )
                        }


                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "AVAILABLE STOCK",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.LightGray
                            )
                            Text(
                                text = "${book.availableCopies} Copies Left",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (book.availableCopies > 0) Color(0xFF2E7D32) else Color(0xFFC62828)
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp), color = Color.LightGray.copy(alpha = 0.3f))

                    Text(
                        text = "Description",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A2C42),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "This is a digital placeholder for the book's description. In a real application, this would contain a summary of the plot, the author's background, and other interesting details about the book.",
                        fontSize = 15.sp,
                        color = Color.Gray,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    Button(
                        onClick = {
                            borrowViewModel.borrowBook(
                                studentId = profileState?.userId ?: "",
                                bookId = book.id,
                                currentAvailableCopies = book.availableCopies
                            )
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A2C42)),
                        enabled = book.availableCopies > 0
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Book, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (book.availableCopies > 0) "Borrow Book" else "Out of Stock",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            } ?: run {
                if (!isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Book not found", color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookDetailPreview() {
     BookDetailScreen("",{}, viewModel(),viewModel(),viewModel())
}
