package ru.netology.nmedia.repository


import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post
import java.net.ConnectException

class PostRepositoryImpl : PostRepository {


    override fun getAllAsync(callback: PostRepository.Callback<List<Post>>) {
        PostsApi.retrofitService.getAll().enqueue(object : retrofit2.Callback<List<Post>> {
            override fun onResponse(
                call: retrofit2.Call<List<Post>>,
                response: retrofit2.Response<List<Post>>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }

                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: retrofit2.Call<List<Post>>, t: Throwable) {
                callback.onError(ConnectException("Connection is lost"))
            }

        })
    }



    override fun saveAsync(post : Post, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.save(post).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException("${response.message()} \n${response.code()}"))
                    return
                }

                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))

            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                callback.onError(ConnectException("Connection is lost"))
            }

        })
    }

    override fun likeByIdAsync(id: Long, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.likeById(id).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException("${response.message()}\n${response.code()}"))
                    return
                }

                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))

            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                callback.onError(ConnectException("Connection is lost"))
            }

        })
    }


    override fun unlikeByIdAsync(id: Long, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.unlikeById(id).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException("${response.message()}\n${response.code()}"))
                    return
                }

                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))

            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                callback.onError(ConnectException("Connection is lost"))
            }

        })
    }





    override fun removeByIdAsync(id: Long,callback: PostRepository.CallbackUnit<Unit>) {
        PostsApi.retrofitService.removeById(id).enqueue(object : retrofit2.Callback<Unit> {
            override fun onResponse(
                call: retrofit2.Call<Unit>,
                response: retrofit2.Response<Unit>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException("${response.message()}\n${response.code()}"))
                    return
                }

                callback.onSuccess()

            }

            override fun onFailure(call: retrofit2.Call<Unit>, t: Throwable) {
                callback.onError(ConnectException("Connection is lost"))
            }

        })
    }

    override fun getByIdAsync(id: Long,  callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.getById(id).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException("${response.message()}\n${response.code()}"))
                    return
                }

                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))

            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                callback.onError(ConnectException("Connection is lost"))
            }

        })
    }


}