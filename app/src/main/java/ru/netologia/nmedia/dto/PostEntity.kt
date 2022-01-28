package ru.netologia.nmedia.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean,
    val likes: Int = 0,
    val share: Int = 0,
    val linkOnYouTube: String? = null
) {

        fun toDto() = Post(id, author, content, published, likedByMe, likes)

        companion object {
            fun fromDto(dto: Post) =
                PostEntity(dto.id, dto.author, dto.content, dto.published, dto.likedByMe, dto.likes)

        }
}