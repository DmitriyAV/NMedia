package ru.netologia.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean,
    val likes: Int = 9099,
    val share: Int = 0,
    val linkOnYouTube: String? = null

)