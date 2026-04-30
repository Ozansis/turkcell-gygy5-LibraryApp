package com.turkcell.libraryapp.data.repository

import com.turkcell.libraryapp.data.model.Book
import com.turkcell.libraryapp.data.supabase.supabase
import io.github.jan.supabase.postgrest.postgrest

class BookRepository {

    suspend fun getAllBooks(): Result<List<Book>> = runCatching {
        supabase.postgrest["books"]
            .select()
            .decodeList<Book>()
    }

    suspend fun getBookById(id: String): Result<Book> = runCatching {
        supabase.postgrest["books"]
            .select { filter { eq("id", id) } }
            .decodeSingle<Book>()
    }



    suspend fun addBook(book: Book): Result<Unit> = runCatching {
        supabase.postgrest["books"].insert(book)
    }


    suspend fun updateBook(id: String, updatedBook: Book) = runCatching {

        supabase.postgrest["books"].update({
            set("title", updatedBook.title)
            set("author", updatedBook.author)
            set("isbn", updatedBook.isbn)
            set("category", updatedBook.category)
            set("page_count", updatedBook.pageCount)
            set("total_copies", updatedBook.totalCopies)
            set("available_copies", updatedBook.avaiableCopies)
        }) {
            filter {
                eq("id", id)
            }
        }

    }

    suspend fun deleteBook(id  : String): Result<Unit> = runCatching {

        supabase.postgrest["books"].delete { filter { eq("id",id) } }
    }

    suspend fun searchBooks(query: String): Result<List<Book>> = runCatching {
        supabase.postgrest["books"].select {
            filter {
                ilike("title", "%$query%")
            }
        }.decodeList<Book>()
    }




}
