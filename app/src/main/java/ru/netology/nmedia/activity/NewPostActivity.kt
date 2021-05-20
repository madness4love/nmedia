package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding.inflate
import ru.netology.nmedia.databinding.NewPostActivityBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = NewPostActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editText.requestFocus()
        binding.ok.setOnClickListener{
            val intent = Intent()
            if (binding.editText.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.editText.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
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