package ru.netologia.nmedia.activity

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
                like.text = PostService.checkCounter(post.like)
                share.text = PostService.checkCounter(post.sher)
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
                                    viewModel.removeById(post.id)
                                    findNavController().navigate(
                                        R.id.action_postFragment_to_feedFragment,
                                        Bundle().apply {
                                            longArg = post.id
                                        }
                                    )
                                    true
                                }
                                R.id.edit_post -> {

                                    viewModel.edit(post.content)
                                    findNavController().navigate(
                                        R.id.action_postFragment_to_editPostFragment,
                                        Bundle().apply {
                                            textArgEdit = post.content
                                        }
                                    )
                                    true
                                }
                                else -> false
                            }
                        }
                    }.show()
                }

                playVideo.setOnClickListener { viewModel.getVideoLink() }

                like.setOnClickListener { viewModel.likeById(post.id) }

                share.setOnClickListener { viewModel.shareById(post.id) }

            }
            findNavController().navigateUp()
        }

        return binding.root
    }

    companion object {
        var Bundle.longArg: Long by PostIdArg
    }
}

