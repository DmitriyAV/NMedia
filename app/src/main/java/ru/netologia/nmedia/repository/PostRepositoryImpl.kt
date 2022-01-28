package ru.netologia.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import ru.netologia.nmedia.dao.PostDao
import ru.netologia.nmedia.dto.Post
import ru.netologia.nmedia.dto.PostEntity

class PostRepositoryImpl(
    private val dao: PostDao
) : PostRepository {

    /*private val data = Transformations.map(dao.get()) { list ->
        list.map {
            Post(it.id, it.author, it.content, it.published, it.likedByMe, it.likes)
        }
    }*/

    override fun get(): LiveData<List<Post>> = dao.get().map { list -> list.map { it.toDto() } }

    override fun save(post: Post) { dao.save(PostEntity.fromDto(post)) }

    override fun videoLink(id: Long) { dao.videoLink(id) }

    override fun likeById(id: Long) { dao.likeById(id) }

    override fun removeById(id: Long) { dao.removeById(id) }

    override fun shareById(id: Long) { dao.shareById(id) }
}
