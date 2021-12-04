package ru.netologia.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.launch

import androidx.activity.viewModels
import ru.netologia.nmedia.R
import ru.netologia.nmedia.adapter.PostActionListener
import ru.netologia.nmedia.viewModel.ViewModelPost
import ru.netologia.nmedia.adapter.PostAdapter
import ru.netologia.nmedia.dto.Post
import ru.netologia.nmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val biding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(biding.root)


        val viewModel: ViewModelPost by viewModels()

        val adapter = PostAdapter(
            object : PostActionListener {
                override fun likeOnPost(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun shareOnPost(post: Post) {
                    viewModel.shareById(post.id)
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }

                    val shareContentChooser =
                        Intent.createChooser(intent, getString(R.string.share))

                    startActivity(shareContentChooser)
                }

                override fun removeOnPost(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun editOnPost(post: Post) {
                    viewModel.editOnPost(post)
                }

                override fun video(post: Post) {
                    viewModel.getVideoLink()
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.linkOnYouTube))
                    startActivity(intent)
                }
            }
        )

        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.edit(result)
            viewModel.save()
        }

        val editPostLauncher =
            registerForActivityResult(EditPostResultContract()) { result ->
                result ?: return@registerForActivityResult
                viewModel.edit(result)
                viewModel.save()
            }

        biding.container.adapter = adapter

        viewModel.data.observe(this, { posts ->
            adapter.submitList(posts)
        })

        viewModel.edited.observe(this) {
            if (it.id == 0L) {
                return@observe
            }
            editPostLauncher.launch(it.content)
        }

        biding.fab.setOnClickListener {
            newPostLauncher.launch()
        }
    }
}