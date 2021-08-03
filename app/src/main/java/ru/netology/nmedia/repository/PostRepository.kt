package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.SingleLiveEvent
import java.lang.Exception


interface PostRepository {
    fun getAllAsync(callback: Callback<List<Post>>)
    fun getByIdAsync(id : Long, callback: Callback<Post>)
    fun likeByIdAsync(id : Long, callback: Callback<Post>)
    fun unlikeByIdAsync(id : Long, callback: Callback<Post>)
    fun saveAsync(post : Post, callback: Callback<Post>)
    fun removeByIdAsync(id: Long, callback: CallbackUnit<Unit>)

    interface Callback<T> {
        fun onError(e : Exception)
        fun onSuccess(posts : T)
    }


    interface CallbackUnit<T> {
        fun onError(e : Exception)
        fun onSuccess()
    }
}