package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.R
import ru.netology.nmedia.dto.WallService
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                date.text = post.published
                content.text = post.content
                txtLiked.text = WallService.displayCount(post.likes)
                txtShare.text = WallService.displayCount(post.shares)
                txtWatch.text = WallService.displayCount(post.watches)
                imgbLiked.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
            }
        }



        binding.imgbLiked.setOnClickListener {
            viewModel.like()
        }

        binding.imgbShare.setOnClickListener {
            viewModel.share()
        }

        binding.imgbWatch.setOnClickListener {
            viewModel.watch()
        }
}


}