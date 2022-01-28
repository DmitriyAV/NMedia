package ru.netologia.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netologia.nmedia.databinding.FragmentEditPostBinding
import ru.netologia.nmedia.util.AndroidUtils
import ru.netologia.nmedia.util.StringArg
import ru.netologia.nmedia.viewModel.ViewModelPost

class EditPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditPostBinding.inflate(
            inflater,
            container,
            false
        )

        val viewModel: ViewModelPost by viewModels(ownerProducer = ::requireParentFragment)

        arguments?.textArgEdit?.let { binding.editPost.setText(it) }

        binding.editPost.requestFocus()
        binding.ok.setOnClickListener {

            val text = binding.editPost.text?.toString()

            if (!text.isNullOrBlank()) {
                viewModel.create(text)
                viewModel.save()
                AndroidUtils.hideKeyboard(requireView())
                findNavController().navigateUp()
            }

        }

        return binding.root
    }

    companion object {
        var Bundle.textArgEdit: String? by StringArg
    }
}
