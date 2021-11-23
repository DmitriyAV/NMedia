package ru.netologia.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netologia.nmedia.R
import ru.netologia.nmedia.databinding.CardPostBinding
import ru.netologia.nmedia.dto.Post
import ru.netologia.nmedia.dto.PostService

interface PostActionListener {
    fun likeOnPost(post: Post)
    fun shareOnPost(post: Post)
    fun removeOnPost(post: Post)
    fun editOnPost(post: Post)
}

class PostAdapter(
    private val listener: PostActionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val biding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(biding, listener)
    }

    override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }
}

class PostViewHolder(

    private val biding: CardPostBinding,
    private val listener: PostActionListener

) : RecyclerView.ViewHolder(biding.root) {

    fun bind(post: Post) {

        with(biding) {

            author.text = post.author
            published.text = post.published
            content.text = post.content
            likeCount.text = PostService.checkCounter(post.like)
            shareCount.text = PostService.checkCounter(post.sher)

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.posts_menu)
                    setOnMenuItemClickListener { token ->
                        when (token.itemId) {
                            R.id.remove_post -> {
                                listener.removeOnPost(post)
                                true
                            }
                            R.id.edit_post -> {
                                listener.editOnPost(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

            like.setImageResource(
                if (post.likedByMe) {
                    R.drawable.ic_liked_24
                } else {
                    R.drawable.ic_baseline_favorite_border_24
                }
            )
            like.setOnClickListener { listener.likeOnPost(post) }

            share.setOnClickListener { listener.shareOnPost(post) }
        }
    }
}
