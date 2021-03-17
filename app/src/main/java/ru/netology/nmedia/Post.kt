package ru.netology.nmedia

data class Post(
    val id : Int,
    val author : String,
    val published : String,
    val content : String,
    var likes : Int = 0,
    var shares : Int = 0,
    var watches : Int = 0,
    var likedByMe : Boolean = false
)