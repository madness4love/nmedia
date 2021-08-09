package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val authorAvatar: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean = false,
    val likes: Int = 0,
) {
    fun toDto() = Post(
        id,
        author,
        authorAvatar,
        published,
        content,
        likedByMe,
        likes
    )

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(
                dto.id,
                dto.author,
                dto.authorAvatar,
                dto.published,
                dto.content,
                dto.likedByMe,
                dto.likes,
            )
    }
}

fun List<PostEntity>.toDto() : List<Post> = map(PostEntity::toDto)
fun List<Post>.toEntity() : List<PostEntity> = map(PostEntity::fromDto)
