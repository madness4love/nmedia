package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import com.google.android.youtube.player.internal.k
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.SingleLiveEvent
import java.lang.Exception


interface PostRepository {
    val data : LiveData<List<Post>>
    suspend fun getAll()
    suspend fun getById (id : Long)
    suspend fun likeById (id : Long)
    suspend fun unlikeById (id : Long)
    suspend fun save (post : Post)
    suspend fun removeById (id: Long)

}