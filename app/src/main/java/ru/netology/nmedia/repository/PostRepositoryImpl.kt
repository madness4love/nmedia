package ru.netology.nmedia.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.ApiError
import ru.netology.nmedia.NetworkError
import ru.netology.nmedia.UnknownAppError
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.entity.toDto
import ru.netology.nmedia.entity.toEntity
import java.io.IOException
import java.lang.Exception

class PostRepositoryImpl(private val dao : PostDao) : PostRepository {

    override val data: LiveData<List<Post>> = dao.getAll().map(List<PostEntity>::toDto)


    override suspend fun getAll() {
        try {
            val response = PostsApi.service.getAll()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(body.toEntity())
        } catch (e : IOException) {
            throw NetworkError
        } catch (e : Exception) {
            throw UnknownAppError
        }
    }



    override suspend fun save (post : Post) {
        try {
            val response = PostsApi.service.save(post)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(PostEntity.fromDto(body))
        } catch (e : IOException) {
            throw NetworkError
        } catch (e : Exception) {
            throw UnknownAppError
        }
    }


    override suspend fun likeById(id: Long) {
        try {

            val response = PostsApi.service.likeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(PostEntity.fromDto(body))

        } catch (e : IOException) {
            throw NetworkError
        } catch (e : Exception) {
            throw UnknownAppError
        }
    }

    override suspend fun unlikeById(id: Long) {
        try {
            val response = PostsApi.service.unlikeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(PostEntity.fromDto(body))

        } catch (e : IOException) {
            throw NetworkError
        } catch (e : Exception) {
            throw UnknownAppError
        }
    }


    override suspend fun removeById(id: Long) {
        try {
            val response = PostsApi.service.removeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            dao.removeById(id)
        } catch (e : IOException) {
            throw NetworkError
        } catch (e : Exception) {
            throw UnknownAppError
        }
    }

    override suspend fun getById(id: Long) {
        try {
            val response = PostsApi.service.getById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.getById(body.id)
        } catch (e : IOException) {
            throw NetworkError
        } catch (e : Exception) {
            throw UnknownAppError
        }
    }


}