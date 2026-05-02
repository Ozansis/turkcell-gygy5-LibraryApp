package com.turkcell.libraryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.libraryapp.data.model.Book
import com.turkcell.libraryapp.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class BookViewModel : ViewModel() {
    private val repository = BookRepository()

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook: StateFlow<Book?> = _selectedBook

    init {
        loadBooks()
    }

    fun getBookById(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _selectedBook.value = _books.value.find { it.id == id }
            
            if (_selectedBook.value == null) {
                 repository.getBookById(id)
                    .onSuccess { _selectedBook.value = it }
                    .onFailure { _error.value = it.message }
            }
            _isLoading.value = false
        }
    }

    fun loadBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            repository
                .getAllBooks()
                .onSuccess { _books.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }


    fun updateBook(id: String, updatedBook: Book) {

        viewModelScope.launch {
            _isLoading.value = true
            repository.updateBook(id, updatedBook)
                .onSuccess {
                    _error.value = null
                    loadBooks()
                }
                .onFailure {
                    _error.value = it.message ?: "Güncelleme başarısız"
                }
            _isLoading.value = false
        }
    }


    fun deleteBook(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.deleteBook(id)
                .onSuccess {
                    _error.value = null
                    loadBooks()
                }
                .onFailure {
                    _error.value = it.message ?: "Silme başarısız"
                }
            _isLoading.value = false
        }
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.searchBooks(query)
                .onSuccess {
                    _books.value = it
                    _error.value = null
                }
                .onFailure {
                    _error.value = it.message ?: "Arama başarısız"
                }
            _isLoading.value = false
        }
    }


    fun decreaseAvailableCopies(bookId: String) {
        _books.value = _books.value.map { book ->
            if (book.id == bookId) {
                book.copy(availableCopies = book.availableCopies - 1)
            } else {
                book
            }
        }
    }
}



