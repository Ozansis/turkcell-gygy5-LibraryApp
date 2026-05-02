package com.turkcell.libraryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.libraryapp.data.model.Book
import com.turkcell.libraryapp.data.model.BorrowRecord
import com.turkcell.libraryapp.data.model.BorrowStatus
import com.turkcell.libraryapp.data.repository.BorrowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class BorrowViewModel : ViewModel() {

    private val repository = BorrowRepository()
    private val _borrows = MutableStateFlow<List<BorrowRecord>>(emptyList())
    val borrows: StateFlow<List<BorrowRecord>> = _borrows.asStateFlow()

    private val _uiState = MutableStateFlow<BorrowUiState>(BorrowUiState.Idle)
    val uiState: StateFlow<BorrowUiState> = _uiState.asStateFlow()

    private val _borrowedBookId = MutableStateFlow<String?>(null)
    val borrowedBookId: StateFlow<String?> = _borrowedBookId.asStateFlow()

    fun getBorrowedBooksUiModels(allBooks: List<Book>): List<BorrowedBookUiModel> {
        return _borrows.value.mapNotNull { record ->
            val book = allBooks.find { it.id == record.bookId } ?: return@mapNotNull null
            
            BorrowedBookUiModel(
                borrowId = record.id,
                bookId = book.id,
                title = book.title,
                author = book.author,
                dueDateDescription = calculateDueDateDescription(record.dueDate),
                status = record.status
            )
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun calculateDueDateDescription(dueDateString: String): String {
        return try {
            val dueDate = Instant.parse(dueDateString)
            val now = Clock.System.now()
            val daysLeft = now.daysUntil(dueDate, TimeZone.UTC)
            
            when {
                daysLeft < 0 -> "Overdue by ${-daysLeft} days"
                daysLeft == 0 -> "Due today"
                else -> "Due in $daysLeft Days"
            }
        } catch (e: Exception) {
            "Due date unknown"
        }
    }

    fun loadMyBorrows(studentId: String) {
        viewModelScope.launch {
            _uiState.value = BorrowUiState.Loading
            repository.getMyBorrows(studentId)
                .onSuccess { records ->
                    _borrows.value = records
                    _uiState.value = BorrowUiState.Idle
                }
                .onFailure { error ->
                    _uiState.value = BorrowUiState.Error(
                        error.message ?: "Kiralamalar yüklenemedi"
                    )
                }
        }
    }

    fun borrowBook(
        studentId: String,
        bookId: String,
        currentAvailableCopies: Int
    ) {
        viewModelScope.launch {
            _uiState.value = BorrowUiState.Loading
            repository.borrowBook(studentId, bookId, currentAvailableCopies)
                .onSuccess { newRecord ->
                    _borrows.value = _borrows.value + newRecord
                    _borrowedBookId.value = bookId
                    _uiState.value = BorrowUiState.BorrowSuccess
                }
                .onFailure { error ->
                    _uiState.value = BorrowUiState.Error(
                        error.message ?: "Ödünç alma başarısız"
                    )
                }
        }
    }
}

data class BorrowedBookUiModel(
    val borrowId: String,
    val bookId: String,
    val title: String,
    val author: String,
    val dueDateDescription: String,
    val status: BorrowStatus
)

sealed class BorrowUiState {
    object Idle : BorrowUiState()
    object Loading : BorrowUiState()
    object BorrowSuccess : BorrowUiState()
    data class Error(val message: String) : BorrowUiState()
}
