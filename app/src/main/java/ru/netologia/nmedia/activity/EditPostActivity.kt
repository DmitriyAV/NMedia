package ru.netologia.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import ru.netologia.nmedia.R
import ru.netologia.nmedia.databinding.ActivityEditPostBinding
import ru.netologia.nmedia.viewModel.ViewModelPost

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val biding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(biding.root)

        val viewModel: ViewModelPost by viewModels()

        with(biding) {
            editPost.requestFocus()
            editPost.setText(intent.getStringExtra(Intent.ACTION_EDIT))
            val intent = Intent()
            ok.setOnClickListener {
                val text = editPost.text?.toString()
                if (text.isNullOrBlank()) {
                    setResult(Activity.RESULT_CANCELED, intent)
                } else {
                    val content = editPost.text.toString()
                    intent.putExtra(Intent.ACTION_EDIT, content)
                    viewModel.edit(content)
                    viewModel.save()


                    setResult(Activity.RESULT_OK, intent)
                }
                finish()
            }
        }
    }
}