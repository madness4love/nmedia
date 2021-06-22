package ru.netology.nmedia.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.youtube.player.internal.g
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import ru.netology.nmedia.dto.Post
import kotlin.random.Random


class FCMService : FirebaseMessagingService() {
    private val action = "action"
    private val content = "content"
    private val likeChannelId = "like"
    private val newPostChannelId = "newPost"
    private val gson = Gson()

    override fun onCreate() {
        super.onCreate()

        createNotificationChannels()

    }

    override fun onMessageReceived(message: RemoteMessage) {

        message.data[action]?.let {
            when (Action.valueOf(it)) {
                Action.LIKE -> handleLike(gson.fromJson(message.data[content], Like::class.java))
                Action.NEW_POST -> handlerNewPost(gson.fromJson(message.data[content], NewPost::class.java))
            }
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_remote_name)
            val descriptionTextLike = getString(R.string.channel_remote_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channelLike = NotificationChannel(likeChannelId, name, importance).apply {
                description = descriptionTextLike
            }
            val channelNewPost = NotificationChannel(newPostChannelId, name, importance).apply {
                description = descriptionTextLike
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channelLike)
            manager.createNotificationChannel(channelNewPost)
        }
    }

    private fun handleLike(content: Like) {
        val notification = NotificationCompat.Builder(this, likeChannelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                getString(
                    R.string.notification_user_liked,
                    content.userName,
                    content.postAuthor,
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }

    private fun handlerNewPost(content : NewPost) {
        val notification = NotificationCompat.Builder(this, newPostChannelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                getString(
                R.string.notification_user_new_post,
                content.userName,
            ))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }


}

enum class Action {
    LIKE,
    NEW_POST,
}

data class Like(
    val userId: Long,
    val userName: String,
    val postId: Long,
    val postAuthor: String,
)

data class NewPost(
    val userId: Long,
    val userName: String,
    val postId: Long,
    val postContent: String,
)