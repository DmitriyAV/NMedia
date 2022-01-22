package ru.netologia.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netologia.nmedia.databinding.FragmentNewPostBinding
import ru.netologia.nmedia.util.AndroidUtils
import ru.netologia.nmedia.util.StringArg
import ru.netologia.nmedia.viewModel.ViewModelPost

class NewPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )

        val viewModel: ViewModelPost by viewModels(ownerProducer = ::requireParentFragment)

        arguments?.textArgNewPost?.let { binding.newPost.setText(it) }

        binding.newPost.requestFocus()
        binding.ok.setOnClickListener {

            val text = binding.newPost.text.toString()

            if (text.isNotBlank()) {
                viewModel.edit(text)
                viewModel.save()
                AndroidUtils.hideKeyboard(requireView())
                findNavController().navigateUp()
            }
        }
        return binding.root
    }

    companion object {
        var Bundle.textArgNewPost: String? by StringArg
    }
}