package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositoryInFileImpl(
    private val context : Context) : PostRepository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val fileName = "posts.json"
    private var nextId = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(fileName)
        if (file.exists()) {
            context.openFileInput(fileName).bufferedReader().use {
                posts = gson.fromJson(it, type)
                data.value = posts
                if (posts.isNotEmpty()) {
                    nextId = posts.maxByOrNull { it.id }!!.id + 1L
                }
            }
        } else {
            sync()
        }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id : Long) {
        posts = posts.map {
            if(it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1)
        }
        data.value = posts
        sync()
    }

    override fun shareById(id : Long) {
        posts = posts.map {
            if(it.id != id) it else it.copy(
                sharedByMe = !it.sharedByMe,
                shares = if (it.sharedByMe) it.shares - 1 else it.shares + 1)
        }
        data.value = posts
        sync()

    }

    override fun watchById(id: Long) {
        posts = posts.map {
            if(it.id != id) it else it.copy(
                watches = it.watches + 1)
        }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(post.copy(
                id = nextId++,
                author = "me",
                content = post.content,
                published = "now"
            )) + posts
            data.value = posts
            sync()
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }

        data.value = posts
        sync()

    }


    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    private fun sync() {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

}