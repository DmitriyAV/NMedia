package ru.netologia.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import ru.netologia.nmedia.ViewModel.ViewModelPost
import ru.netologia.nmedia.databinding.ActivityMainBinding
import ru.netologia.nmedia.dto.Post
import ru.netologia.nmedia.dto.PostService
import ru.netologia.nmedia.repository.PostRepository

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

                like.setImageResource(
                if (post.likedByMe) { R.drawable.ic_liked_24 }
                else { R.drawable.ic_baseline_favorite_border_24 })

            }

            biding.like.setOnClickListener {
                Log.d("click Like", "like")
                viewModel.like()
                biding.likeCount?.text = PostService.checkCounter(post.like)
            }

            biding.share.setOnClickListener {
                Log.d("click share", "share")
                viewModel.share()
                biding.shareCount?.text = PostService.checkCounter(post.sher)
            }
        }
    }
}