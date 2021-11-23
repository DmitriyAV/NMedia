package ru.netologia.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean = false,
    val like: Int = 9099,
    val sher: Int = 0
)