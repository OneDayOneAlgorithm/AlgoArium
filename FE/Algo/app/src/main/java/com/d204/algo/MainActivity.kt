package com.d204.algo

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.d204.algo.databinding.ActivityMainBinding
import com.d204.algo.presentation.utils.Constants
import com.d204.algo.presentation.utils.StompHandler
import com.d204.algo.presentation.viewmodel.MainActivityViewModel
import com.d204.algo.ui.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "MainActivity"
private const val ACTION_ANIM_TIME = 2_000L

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private val espHelper = ApplicationClass.preferencesHelper
    private lateinit var binding: ActivityMainBinding
    private lateinit var bubbleTransition: GifDrawable
    private lateinit var rankUpEffect: GifDrawable
    private lateinit var outTransition: GifDrawable
    var doubleBackToExit = false
    // 로그인 activity에서 토큰을 받을 필요가 있으면 사용하세요.
    // private val kakaoToken = intent.extras?.getString("kakaoToken")
    // 로그인 activity에서 시즌정보(프리시즌, 시즌시작) 정보를 받을 필요가 있으면 사용하세요
    // private val season = intent.extras?.getString("season")

    // 웹소켓
    @Inject
    lateinit var stompClient: StompHandler

    // Glide
    @Inject
    lateinit var glide: RequestManager

    // fragment 이동 화면 애니메이션 종료 여부
    var isAnimationFinished = true

    // 소켓 연결
    companion object {
        val pcConnectionNumber = (10000000..99999999).random().toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) // 화면 꽉차게
        binding = ActivityMainBinding.inflate(layoutInflater)
//        setAnimation() // 새 시즌 시작이 아니면 바로 호출
        setRankAnimation() // 새 시즌으로 랭크업 해야하면 호출 후 클릭 시 setAnimation 호출
        setOutAnimation() // 프래그먼트 popup animation 추가
        setContentView(binding.root)
        setupNavHost()
        connectSocket()
//        카카오 앱 개발자 페이지에 등록하기
//        var keyHash = Utility.getKeyHash(this)
//        Log.d(TAG, "onCreate: $keyHash")
//        showSnackBar(binding.root, "ㅎㅇ")
        initBackPress()
    }

    private fun setupNavHost() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
    }

    private fun connectSocket() {
        try {
            stompClient.connectStomp(pcConnectionNumber)
        } catch (e: Exception) {
            showSnackBar(binding.root, "PC연결 오류 앱을 재시작해주세요")
        }
    }

    private fun removeAnimation() {
        val animation = AlphaAnimation(1.0f, 0.0f) // 투명도 1.0에서 0.0으로 애니메이션
        animation.duration = 2000 // 2초 동안
        binding.activityMainUpperBg.startAnimation(animation)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                binding.activityMainUpperBg.visibility = View.GONE // 뷰를 숨기기
                // 프리시즌인지 체크하여 승급문제 fragment로 이동하기
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setAnimation() {
        glide.asGif()
            .load(R.drawable.bubble_transition)
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
                    bubbleTransition = resource!!
                    bubbleTransition.setLoopCount(1)

                    bubbleTransition.registerAnimationCallback(object :
                        Animatable2Compat.AnimationCallback() {
                        override fun onAnimationEnd(drawable: Drawable?) {
                            binding.transitionAnim.visibility = View.GONE
                        }
                    })

                    removeAnimation()
                    return false
                }
            })
            .into(binding.transitionAnim)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setOutAnimation() {
        glide.asGif()
            .load(R.drawable.waterfall)
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
                    outTransition = resource!!
                    outTransition.setLoopCount(1)
                    Log.d(TAG, "onResourceReady: $outTransition")

                    outTransition.registerAnimationCallback(object :
                        Animatable2Compat.AnimationCallback() {
                        override fun onAnimationEnd(drawable: Drawable?) {
                            binding.transitionOutAnim.visibility = View.GONE
                        }
                    })

                    return false
                }
            })
            .into(binding.transitionOutAnim)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setRankAnimation() {
        // 처음에 아무 것도 안하는 클릭이벤트를 달아야 아래에 겹쳐진 뷰로 클릭이벤트 전달을 막을 수 있음
        binding.activityMainRankUp.setOnClickListener {}

        with(binding) {
            val prefTier = if (espHelper.prefUserPreTier == 0) 1 else espHelper.prefUserPreTier
            val curTier = if (espHelper.prefUserTier == 0) 1 else espHelper.prefUserTier
            Log.d(TAG, "setRankAnimation: $prefTier, $curTier")
            if (ApplicationClass.skinOn) {
                activityMainTierBefore.setImageResource(Constants.RANK_TIER[prefTier])
                activityMainTierAfter.setImageResource(Constants.RANK_TIER[curTier])
            } else {
                activityMainTierBefore.setImageResource(Constants.COPYRIGHT_RANK_TIER[prefTier])
                activityMainTierAfter.setImageResource(Constants.COPYRIGHT_RANK_TIER[curTier])
            }
        }

        val rotateAnimator = ObjectAnimator.ofFloat(binding.activityMainTierBefore, "rotationY", 0f, 10800f)
        val transparentAnimator = ObjectAnimator.ofFloat(binding.activityMainTierBefore, "alpha", 1.0f, 0.0f) // 투명도 1.0에서 0.0으로 애니메이션
        rotateAnimator.duration = 6000
        transparentAnimator.duration = 5500

        val animator = AnimatorSet()
        animator.playTogether(rotateAnimator, transparentAnimator)

        animator.start()

        glide.asGif()
            .load(R.drawable.tier_up_light_transparent_up)
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
                    rankUpEffect = resource!!
                    rankUpEffect.setLoopCount(1)

                    rankUpEffect.registerAnimationCallback(object :
                        Animatable2Compat.AnimationCallback() {
                        override fun onAnimationEnd(drawable: Drawable?) {
                            binding.apply {
                                activityMainRankUpEffect.visibility = View.GONE
                                activityMainTierBefore.visibility = View.GONE
                                activityMainRankUp.setOnClickListener {
                                    it.visibility = View.GONE
                                    setAnimation()
                                }
                            }
                        }
                    })

                    return false
                }
            })
            .into(binding.activityMainRankUpEffect)
    }

    fun callTransition() {
        binding.transitionAnim.visibility = View.VISIBLE
        bubbleTransition.start()
    }

    private fun callOutTransition() {
        binding.transitionOutAnim.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) { binding.transitionOutAnim.visibility = View.GONE }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        binding.transitionOutAnim.startAnimation(animation)
        outTransition.start()
    }

    fun sendSocketMessage(url: String) {
        stompClient.sendStomp(url, pcConnectionNumber)
    }

    // 뒤로가기
    private fun initBackPress() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isAnimationFinished && navController.currentDestination?.id != R.id.navigation_home) {
                    if (navController.currentDestination?.id == R.id.navigation_home) {
                        finish()
                    } else {
                        navController.navigateUp()
                        callOutTransition()
                    }
                } else if (isAnimationFinished && navController.currentDestination?.id == R.id.navigation_home) {
                    if (doubleBackToExit) {
                        finish()
                    } else {
                        Toast.makeText(this@MainActivity, "한번 더 누르면 앱을 종료합니다.", Toast.LENGTH_SHORT).show()
                    }
                    doubleBackToExit = true
                    Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExit = false }, 2000)
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

    // ACTION_ANIM_TIME 동안 화면 클릭 방지
    fun delayClickWhileAnimation() {
        lifecycleScope.launch {
            isAnimationFinished = false
            window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            delay(ACTION_ANIM_TIME)
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            isAnimationFinished = true
        }
    }
}
