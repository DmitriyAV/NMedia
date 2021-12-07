package ru.netologia.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netologia.nmedia.dto.Post

class PostRepositoryFileImpl(
    private val context: Context
) : PostRepository {

    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)
    private val gson = Gson()
    private val filename = "post.json"
    private val type = TypeToken.getParameterized(List::class.java,Post::class.java).type


    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        } else { sync() }
    }

    override fun get(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id == id) {
                it.copy(
                    likedByMe = !it.likedByMe,
                    like = if (!it.likedByMe) {
                        it.like + 1
                    } else {
                        it.like - 1
                    }
                )
            } else {
                it
            }
        }
        data.value = posts
        sync()
    }

    override fun shareById(id: Long) {

        data.value = data.value?.map {
            if (it.id == id) {
                it.copy(
                    sher = it.sher + 1
                )
            } else it
        }
        sync()
    }

    override fun removeById(id: Long) {
        data.value = posts.filter { it.id != id }
        sync()
    }

    override fun save(post: Post) {
        val published = "Now"
        val author = "Me"
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = posts.firstOrNull()?.id?.inc() ?: 0,
                    author = author,
                    published = published
                )
            ) + posts
            data.value = posts
            return
        } else {
            posts = posts.map {
                if (it.id == post.id) {
                    it.copy(content = post.content)
                } else it
            }
            data.value = posts
        }
        sync()
    }

    override fun videoLink() {
        data.value = posts
        sync()
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }
}