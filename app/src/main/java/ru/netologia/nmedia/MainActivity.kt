package ru.netologia.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netologia.nmedia.databinding.ActivityMainBinding
import ru.netologia.nmedia.dto.Post
import ru.netologia.nmedia.dto.PostService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val biding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(biding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу." +
                    " Затем появились курсы по дизайну, разработке, аналитике и управлению. " +
                    "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. " +
                    "Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. " +
                    "Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb"
        )

        with(biding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likeCount?.text = PostService.checkCounter(post.like)
            shareCount?.text = PostService.checkCounter(post.sher)

            if (post.likedByMe){
                like.setImageResource(R.drawable.ic_liked_24)
            }
            else {
                like.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            like.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) {
                    like?.setImageResource(R.drawable.ic_liked_24)
                }
                else {
                    like?.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }

                if (post.likedByMe) {
                    post.like + 1
                } else {
                    post.like - 1
                }
                likeCount.text = PostService.checkCounter(post.like)
            }

            share.setOnClickListener {
                    post.sher + 1
                }
                shareCount.text = PostService.checkCounter(post.sher)
            }
        }
    }
