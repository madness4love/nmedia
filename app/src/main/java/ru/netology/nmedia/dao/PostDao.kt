package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostDao {
    fun getAll() : List<Post>
    fun likeById(id : Long)
    fun shareById(id : Long)
    fun watchById(id : Long)
    fun save(post: Post) : Post
    fun removeById(id: Long)
}