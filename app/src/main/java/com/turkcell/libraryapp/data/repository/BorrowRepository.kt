package com.turkcell.libraryapp.data.repository

import com.turkcell.libraryapp.data.model.BorrowRecord
import com.turkcell.libraryapp.data.supabase.supabase
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class BorrowRepository() {

    @OptIn(ExperimentalTime::class)
    suspend fun borrowBook(
        studentId: String,
        bookId: String,
        currentAvailableCopies: Int
    ): Result<BorrowRecord> {
        return try {

            if (currentAvailableCopies <= 0) {
                return Result.failure(Exception("Stokta kitap kalmadı"))
            }

            val now = Clock.System.now()
            val dueDate = now.plus(5, DateTimeUnit.DAY, TimeZone.UTC)

            val newRecord = BorrowRecord(
                studentId = studentId,
                bookId = bookId,
                dueDate = dueDate.toString()
            )

            val inserted = supabase.from("borrow_records")
                .insert(newRecord) {
                    select()
                }
                .decodeSingle<BorrowRecord>()

            supabase.from("books")
                .update({
                    set("available_copies", currentAvailableCopies - 1)
                }) {
                    filter {
                        eq("id", bookId)
                    }
                }


            Result.success(inserted)
        } catch (e: Exception) {
            Result.failure(e)
        }


    }


    suspend fun getMyBorrows(studentId: String): Result<List<BorrowRecord>> {
        return try {
            val records = supabase.from("borrow_records")
                .select {
                    filter {
                        eq("student_id", studentId)
                    }
                }
                .decodeList<BorrowRecord>()

            Result.success(records)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }





}





