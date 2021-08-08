package ru.netology.nmedia.viewModel

import android.app.Application
import android.net.Network
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import ru.netology.nmedia.NetworkError
import ru.netology.nmedia.db.AppDB
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.model.FeedModelState
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.util.SingleLiveEvent
import java.io.IOException
import java.lang.Exception
import java.net.ConnectException
import kotlin.concurrent.thread

private val empty = Post(
    id = 0L,
    content = "",
    author = "",
    published = "",
    authorAvatar = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository =
        PostRepositoryImpl(AppDB.getInstance(context = application).postDao())

    val data: LiveData<FeedModel> = repository.data.map(::FeedModel)
    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState :LiveData<FeedModelState>
            get() = _dataState

    private val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPosts()
    }


    fun loadPosts() = viewModelScope.launch {
        try {
            _dataState.value = FeedModelState(loading = true)
            repository.getAll()
            _dataState.value = FeedModelState()
        } catch (e : Exception) {
            _dataState.value = FeedModelState(error = true)
        }
    }

    fun refreshPosts() = viewModelScope.launch {
        try {
            _dataState.value = FeedModelState(refreshing = true)
            repository.getAll()
            _dataState.value = FeedModelState()
        } catch (e : Exception) {
            _dataState.value = FeedModelState(error = true)
        }
    }

    fun save() {
       edited.value?.let {
           _postCreated.value = Unit
           viewModelScope.launch {
               try {
                   repository.save(it)
                   _dataState.value = FeedModelState()
               } catch (e : Exception) {
                   _dataState.value = FeedModelState(error = true)
               }
           }
       }
        edited.value = empty

    }

    fun like(id : Long) = viewModelScope.launch{
        val post = repository.getById(id)


    }

    fun likeById(id: Long) {

    }

    fun unlikeById(id : Long) {

    }

    fun removeById(id: Long) {

    }



    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        var text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

}