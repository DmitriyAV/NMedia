package ru.netologia.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netologia.nmedia.ViewModel.ViewModelPost
import ru.netologia.nmedia.databinding.ActivityMainBinding
import ru.netologia.nmedia.dto.PostService


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val biding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(biding.root)

        val viewModel: ViewModelPost by viewModels()

        viewModel.get().observe(this) { post ->
            with(biding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likeCount?.text = PostService.checkCounter(post.like)
                shareCount?.text = PostService.checkCounter(post.sher)

                like.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.ic_liked_24
                    } else {
                        R.drawable.ic_baseline_favorite_border_24
                    }
                )
            }
        }
        biding.like.setOnClickListener { viewModel.like() }
        biding.share.setOnClickListener { viewModel.share() }
    }
}