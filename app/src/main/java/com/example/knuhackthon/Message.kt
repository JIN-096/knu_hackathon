package com.example.knuhackthon

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Message(
    val message: String? = null,
    val createdAt : String? = null,
    val name : String? = null,
    val uid: String? = null
)
