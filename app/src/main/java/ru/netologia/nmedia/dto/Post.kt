package ru.netologia.nmedia.dto

data class Post(
    val id: Int,
    val author: String,
    val published: String,
    val content: String,
    var likedByMe: Boolean = false,
    var like: Int = 6099,
    var sher: Int = 2000
)