package ru.netologia.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netologia.nmedia.R
import ru.netologia.nmedia.databinding.CardPostBinding
import ru.netologia.nmedia.dto.Post
import ru.netologia.nmedia.dto.PostService

class PostAdapter(
    private val onPostClicked: (Post) -> Unit
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val biding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(biding, onPostClicked)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(
    private val biding: CardPostBinding,
    private val onPostClicked: (Post) -> Unit
) : RecyclerView.ViewHolder(biding.root) {

    fun bind(post: Post) {
        with(biding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likeCount?.text = PostService.checkCounter(post.like)
            shareCount?.text = PostService.checkCounter(post.sher)

            like.setImageResource(if (post.likedByMe) { R.drawable.ic_liked_24
            } else { R.drawable.ic_baseline_favorite_border_24
                }
            )
            like.setOnClickListener { onPostClicked(post) }
            share.setOnClickListener { onPostClicked(post) }
        }
    }
}
