package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.new_post_activity.view.*
import ru.netology.nmedia.databinding.EditPostActivityBinding
import ru.netology.nmedia.viewModel.PostViewModel


class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = EditPostActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


     //   val viewModel: PostViewModel by viewModels()


        with(binding.editText) {
            requestFocus()
            val text = intent.getStringExtra("post text input")
            setText(text)
        }

            binding.ok.setOnClickListener{
                val intent = Intent()
                if (binding.editText.text.isNullOrBlank()) {
                    setResult(Activity.RESULT_CANCELED, intent)
                } else {
                    val content = binding.editText.text.toString()
                    intent.putExtra("post text output", content)
                    setResult(Activity.RESULT_OK, intent)
                }

                finish()

            }

            binding.cancelButton.setOnClickListener{
                val intent = Intent()
                setResult(Activity.RESULT_CANCELED, intent)
                finish()
            }
        }



    }
