package com.dung.nhom6cuahangthucung.model

import java.text.SimpleDateFormat
import java.util.*

data class Invoice(
    var id: String,
    var username: String,
    var petID: String,
    var Date: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(
        Date()
    )
)