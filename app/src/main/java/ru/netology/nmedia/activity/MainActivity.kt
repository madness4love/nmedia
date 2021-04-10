package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.dto.WallService
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(object : OnInteractionListener{
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }
            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }
            override fun onWatch(post: Post) {
                viewModel.watchById(post.id)
            }
            override fun onEdit(post: Post) {
                viewModel.edit(post)
                binding.cancelView.visibility = View.VISIBLE
                currentText.text = post.content
            }
            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

        })

        binding.cancelView.visibility = View.GONE
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) {post ->
            if (post.id == 0L) {
                return@observe
            }


            with(binding.editContent) {
                requestFocus()
                setText(post.content)
            }

            with(binding.currentText) {
                text = post.content
            }
        }

        binding.save.setOnClickListener{
            with(binding.editContent) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                if (binding.cancelView.visibility == View.VISIBLE) {
                    binding.cancelView.visibility = View.GONE
                }

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }

        binding.cancelButton.setOnClickListener {
            binding.cancelView.visibility = View.GONE
            with(binding.editContent) {
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }



    }
}
