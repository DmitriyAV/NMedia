package ru.netologia.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netologia.nmedia.R
import ru.netologia.nmedia.activity.EditPostFragment.Companion.textArgEdit
import ru.netologia.nmedia.activity.PostFragment.Companion.longArg
import ru.netologia.nmedia.adapter.PostActionListener
import ru.netologia.nmedia.viewModel.ViewModelPost
import ru.netologia.nmedia.adapter.PostAdapter
import ru.netologia.nmedia.dto.Post
import ru.netologia.nmedia.databinding.FeedFragmentBinding


class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FeedFragmentBinding.inflate(
            inflater,
            container,
            false,
        )

        val viewModel: ViewModelPost by viewModels( ownerProducer = ::requireParentFragment )

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
                    arguments?.textArgEdit = post.content
                       Bundle().apply {
                          textArgEdit = post.content
                       }

                    viewModel.editOnPost(post)
                }

                override fun video(post: Post) {
                    viewModel.getVideoLink()
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.linkOnYouTube))
                    startActivity(intent)
                }

                override fun getPost(post: Post) {
                    findNavController().navigate(
                        R.id.action_feedFragment_to_postFragment,
                        Bundle().apply {
                            longArg = post.id
                        }
                    )
                }
            }
        )

        val id = let { arguments?.longArg }
        id?.let { viewModel.removeById(it) }

        binding.container.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(viewLifecycleOwner) {
            if (it.id == 0L) {
                return@observe
            }
           findNavController().navigate(
               R.id.action_feedFragment_to_editPostFragment,
               Bundle().apply { textArgEdit = it.content }
           )
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(
                R.id.action_feedFragment_to_newPostFragment
            )
        }

        return binding.root
    }

}