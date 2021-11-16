package ru.netologia.nmedia.dto

data class Post(
    val id: Int,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean = false,
    val like: Int = 999,
    val sher: Int = 999
)