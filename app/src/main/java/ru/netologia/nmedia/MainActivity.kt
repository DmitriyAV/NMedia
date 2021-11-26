package ru.netologia.nmedia

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import ru.netologia.nmedia.adapter.PostActionListener
import ru.netologia.nmedia.viewModel.ViewModelPost
import ru.netologia.nmedia.adapter.PostAdapter
import ru.netologia.nmedia.databinding.ActivityMainBinding
import ru.netologia.nmedia.dto.Post
import ru.netologia.nmedia.util.AndroidUtils


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
                }

                override fun removeOnPost(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun editOnPost(post: Post) {
                    viewModel.editOnPost(post)
                }
            }
        )
        biding.container.adapter = adapter

        viewModel.data.observe(this, { posts ->
            adapter.submitList(posts)
        })

        with(biding) {

            content.addTextChangedListener {
                save.visibility = View.VISIBLE
                clearText.visibility = View.VISIBLE
                save.setOnClickListener save@{ save ->

                    val text = content.text?.toString()
                    if (text.isNullOrBlank()) {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.error_blank),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@save
                    }

                    viewModel.edit(text)
                    viewModel.save()
                    content.setText("")
                    content.clearFocus()
                    group.visibility = View.GONE
                    AndroidUtils.hideKeyboard(content)

                }
                clearText.setOnClickListener {
                    content.setText("")
                    clearText.visibility = View.GONE
                }
            }
            viewModel.edited.observe(this@MainActivity, {
                if (it.id == 0L) {
                    return@observe
                }
                content.requestFocus()
                content.setText(it.content)

            })
        }
    }
}