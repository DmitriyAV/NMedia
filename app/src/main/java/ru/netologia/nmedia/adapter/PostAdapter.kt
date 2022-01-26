package ru.netologia.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
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
    fun video(post: Post)
    fun getPost(post: Post)

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
    private val binding: CardPostBinding,
    private val listener: PostActionListener

) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.isChecked = post.likedByMe
            like.text = PostService.checkCounter(post.likes)
            share.text = PostService.checkCounter(post.share)
            videoLink.text = post.linkOnYouTube


            if (!post.linkOnYouTube.isNullOrEmpty()) {
                groupForVideo.visibility = View.VISIBLE
            } else {
                groupForVideo.visibility = View.GONE
            }

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

            playVideo.setOnClickListener { listener.video(post) }

            like.setOnClickListener { listener.likeOnPost(post) }

            share.setOnClickListener { listener.shareOnPost(post) }

            content.setOnClickListener { listener.getPost(post) }
        }
    }
}
