package com.d204.algo

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.d204.algo.data.repository.UserRepository
import com.d204.algo.databinding.ActivityLoginBinding
import com.d204.algo.ui.oauth.KaKaoApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "LoginActivity"

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var kakaoApi: KaKaoApi
    private lateinit var clickRipple: GifDrawable

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var glide: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) // 화면 꽉차게
        binding = ActivityLoginBinding.inflate(layoutInflater)
        kakaoApi = KaKaoApi(this, userRepository)
        kakaoApi.setLoginBtn(binding.btnKakao)
        setVideo()
        setClickEvent()
        setClickAnimation() // ContraintLayout 0dp로 하면 클릭 시 화면이 왼쪽으로 치우치는 문제가 있음 -> match_parent 로 해결 why?
        setContentView(binding.root)
    }

    private fun setVideo() {
        val videoPath = "android.resource://" + packageName + "/" + R.raw.login
        binding.loginBg.apply {
            setOnCompletionListener {
                start()
            }
            setVideoURI(Uri.parse(videoPath))
            start()
        }
    }

    private fun setClickEvent() {
        binding.activityLoginIsCopyright.setOnCheckedChangeListener { checked ->
            ApplicationClass.skinOn = checked
        }
    }

    override fun onResume() {
        super.onResume()
        setVideo()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setClickAnimation() {
        glide.asGif()
            .load(R.drawable.ripple_effect_fast)
            .listener(object : RequestListener<GifDrawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    clickRipple = resource!!
                    clickRipple.setLoopCount(1)

                    clickRipple.registerAnimationCallback(object :
                        Animatable2Compat.AnimationCallback() {
                        override fun onAnimationEnd(drawable: Drawable?) {
                            binding.touchRipple.visibility = View.INVISIBLE
                        }
                    })

                    return false
                }
            })
            .into(binding.touchRipple)

        binding.activityLogin.setOnTouchListener { _, event ->
            val x = event.x
            val y = event.y
            binding.touchRipple.visibility = View.INVISIBLE // 뷰안보이면 애니메이션도 종료됨
            // 터치된 위치에서 GIF를 표시합니다.
            showGifAtPosition(x, y)
            true
        }
    }

    // 이동 시에 마지막 물결이 보이는 문제가 있음. 별 짓을 다 해봐도 안고쳐진다. -> adb 에서 성능이 낮아서 생기는 문제(실물에서는 없는 현상)
    private fun showGifAtPosition(x: Float, y: Float) {
        clickRipple.startFromFirstFrame()

        // GIF를 터치된 위치로 이동합니다.
        val touchLayout = binding.touchRipple.layoutParams as ConstraintLayout.LayoutParams
        touchLayout.leftMargin = x.toInt() - binding.touchRipple.layoutParams.width / 2
        touchLayout.topMargin = y.toInt() - binding.touchRipple.layoutParams.height / 2
        binding.touchRipple.layoutParams = touchLayout

        binding.touchRipple.visibility = View.VISIBLE
    }
}
