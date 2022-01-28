package ru.netologia.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netologia.nmedia.R
import ru.netologia.nmedia.activity.EditPostFragment.Companion.textArgEdit
import ru.netologia.nmedia.databinding.FragmentSinglePostBinding
import ru.netologia.nmedia.dto.PostService
import ru.netologia.nmedia.util.PostIdArg
import ru.netologia.nmedia.viewModel.ViewModelPost


class PostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSinglePostBinding.inflate(
            inflater,
            container,
            false
        )

        val viewModel: ViewModelPost by viewModels(ownerProducer = ::requireParentFragment)

        val id = arguments?.longArg

        viewModel.data.observe(viewLifecycleOwner) { posts ->

            val post = posts.first { post -> post.id == id }

            binding.postInclude.apply {

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

                                    arguments?.longArg = post.id
                                    findNavController().navigate(
                                        R.id.action_postFragment_to_feedFragment,
                                        Bundle().apply {
                                            longArg = post.id
                                        }
                                    )

                                    true
                                }
                                R.id.edit_post -> {

                                    arguments?.textArgEdit = post.content
                                    Bundle().apply {
                                        textArgEdit = post.content
                                    }
                                    viewModel.editOnPost(post)

                                    true
                                }
                                else -> false
                            }
                        }
                    }.show()
                }

                playVideo.setOnClickListener { viewModel.getVideoLink(post.id) }

                like.setOnClickListener { viewModel.likeById(post.id) }

                share.setOnClickListener {
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

            }

        }

        viewModel.edited.observe(viewLifecycleOwner) {
            if (it.id == 0L) {
                return@observe
            }
            findNavController().navigate(
                R.id.action_postFragment_to_editPostFragment,
                Bundle().apply { textArgEdit = it.content }
            )
        }

        return binding.root
    }

    companion object {
        var Bundle.longArg: Long by PostIdArg
    }
}

