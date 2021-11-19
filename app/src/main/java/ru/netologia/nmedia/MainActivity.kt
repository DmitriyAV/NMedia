package ru.netologia.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netologia.nmedia.ViewModel.ViewModelPost
import ru.netologia.nmedia.adapter.PostAdapter
import ru.netologia.nmedia.databinding.ActivityMainBinding
import ru.netologia.nmedia.databinding.CardPostBinding
import ru.netologia.nmedia.dto.PostService


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val biding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(biding.root)

        val viewModel: ViewModelPost by viewModels()
        val adapter = PostAdapter (
            { viewModel.likeById(it.id) },
            { viewModel.shareById(it.id) }
        )
        biding.container.adapter = adapter
        viewModel.get().observe(this) { posts ->
            adapter.submitList(posts)
        }
    }
}