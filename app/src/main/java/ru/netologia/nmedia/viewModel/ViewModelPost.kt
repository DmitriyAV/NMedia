package ru.netologia.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netologia.nmedia.dto.Post
import ru.netologia.nmedia.repository.PostRepositoryFileImpl
import ru.netologia.nmedia.repository.PostRepository

private val empty = Post(
    0L,
    "",
    "",
    "",
    false,
    0,
    0
)

class ViewModelPost(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositoryFileImpl(application)

    val edited = MutableLiveData(empty)

    val data: LiveData<List<Post>> = repository.get()

    fun likeById(id: Long) = repository.likeById(id)

    fun shareById(id: Long) = repository.shareById(id)

    fun removeById(id: Long) = repository.removeById(id)

    fun editOnPost(post: Post) { edited.value = post }

    fun getVideoLink() = repository.videoLink()

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