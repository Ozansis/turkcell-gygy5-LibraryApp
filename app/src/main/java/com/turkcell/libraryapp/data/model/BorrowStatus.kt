package com.turkcell.libraryapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class BorrowStatus {
    @SerialName("active") ACTIVE,
    @SerialName("returned") RETURNED,
    @SerialName("overdue") OVERDUE
}