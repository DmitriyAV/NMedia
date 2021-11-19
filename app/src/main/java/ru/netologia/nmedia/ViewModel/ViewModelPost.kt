package ru.netologia.nmedia.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netologia.nmedia.dto.Post
import ru.netologia.nmedia.repository.PostRepository
import ru.netologia.nmedia.repository.PostRepositoryInMemory

class ViewModelPost: ViewModel() {
   private val repository: PostRepository = PostRepositoryInMemory()

    fun get(): LiveData<List<Post>> = repository.get()
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)

}