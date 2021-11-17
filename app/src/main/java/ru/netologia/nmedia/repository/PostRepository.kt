package ru.netologia.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netologia.nmedia.dto.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun share()
}