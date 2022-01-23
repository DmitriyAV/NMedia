package ru.netologia.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netologia.nmedia.R
import ru.netologia.nmedia.activity.EditPostFragment.Companion.textArgEdit
import ru.netologia.nmedia.activity.NewPostFragment.Companion.textArgNewPost
import ru.netologia.nmedia.activity.PostFragment.Companion.longArg
import ru.netologia.nmedia.databinding.ActivityAppBinding


class ActivityApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)

            if (text.isNullOrBlank()) {
                Snackbar.make(binding.root, R.string.error_empty_content, LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok) {
                        finish()
                    }
                    .show()
                return@let
            }

            findNavController(R.id.navigation).navigate(
                R.id.action_feedFragment_to_newPostFragment,
                Bundle().apply {
                    textArgNewPost = text
                }
            )

            findNavController(R.id.navigation).navigate(
                R.id.action_feedFragment_to_editPostFragment,
                Bundle().apply {
                    textArgEdit = text
                }
            )

            findNavController(R.id.navigation).navigate(
                R.id.action_postFragment_to_editPostFragment,
                Bundle().apply {
                    textArgEdit = text
                }
            )

            findNavController(R.id.navigation).navigate(
                R.id.action_feedFragment_to_postFragment,
                Bundle().apply {
                    longArg = text.toLong()
                }

            )

            findNavController(R.id.navigation).navigate(
                R.id.action_postFragment_to_editPostFragment,
                Bundle().apply {
                    textArgEdit = text
                }
            )

        }
    }
}