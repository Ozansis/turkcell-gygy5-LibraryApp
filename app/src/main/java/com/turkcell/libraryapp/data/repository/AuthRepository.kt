package com.turkcell.libraryapp.data.repository

import io.github.jan.supabase.auth.OtpType
import kotlinx.coroutines.delay
import kotlin.random.Random

class AuthRepository {
    suspend fun signIn(email: String, password: String): Result<Unit> = runCatching {
        delay(2000) // dışarıya istek atıyomuş gibi

        val isSuccess = Random.nextBoolean() // %50 %50
        if (isSuccess)
            Unit
        else
            throw Exception("Fake login failed")
    }

    suspend fun register(email: String, password: String, repeatPassword: String, username: String): Result<Unit> =
        runCatching {
            delay(2000)


            if (username.lowercase() == "admin") {
                throw Exception("Bu kullanıcı adı zaten alınmış!")
            }



            Unit
        }


}