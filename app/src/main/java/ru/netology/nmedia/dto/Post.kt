package ru.netology.nmedia.dto

import android.net.Uri

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likes: Int = 0,
    val shares: Int = 0,
    val watches: Int = 0,
    val likedByMe: Boolean = false,
    val sharedByMe: Boolean = false,
    val videoUrl: String? = null
)