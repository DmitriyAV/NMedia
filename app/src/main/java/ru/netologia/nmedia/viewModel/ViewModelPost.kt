package ru.netologia.nmedia.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netologia.nmedia.dto.Post
import ru.netologia.nmedia.repository.PostRepository
import ru.netologia.nmedia.repository.PostRepositoryInMemory

private val empty = Post(
    0L,
    "",
    "",
    "",
    false,
    0,
    0
)

class ViewModelPost : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemory()

    val edited = MutableLiveData(empty)

    fun get(): LiveData<List<Post>> = repository.get()

    fun likeById(id: Long) = repository.likeById(id)

    fun shareById(id: Long) = repository.shareById(id)

    fun removeById(id: Long) = repository.removeById(id)

    fun editOnPost(post: Post) { edited.value = post }

    fun edit(text: String) {
        val formatted = text.trim()
        if (edited.value?.content == formatted){
            return
        }
        edited.value = edited.value?.copy(content = formatted)
    }

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

}