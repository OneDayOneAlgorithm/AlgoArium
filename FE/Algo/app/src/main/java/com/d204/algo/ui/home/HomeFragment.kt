package com.d204.algo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.d204.algo.ApplicationClass
import com.d204.algo.MainActivity
import com.d204.algo.R
import com.d204.algo.base.BaseFragment
import com.d204.algo.base.BaseViewModel
import com.d204.algo.databinding.FragmentHomeBinding
import com.d204.algo.presentation.viewmodel.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, BaseViewModel>() {
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)
    override val viewModel: HomeFragmentViewModel by viewModels()
    private lateinit var coralGenerator: CoralGenerator

//    @Inject
//    lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        coralGenerator = CoralGenerator(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInitSettings()
        setSkin()
        setCode()
    }

    override fun onResume() {
        super.onResume()
        coralGenerator.isRunning = true
        coralGenerator.generateCoral()
    }

    override fun onPause() {
        super.onPause()
        coralGenerator.isRunning = false
    }

    private fun setCode() {
        binding.fragmentHomeConnectionCode.text = "PC연결 : ${MainActivity.pcConnectionNumber}"
    }

    private fun setSkin() {
        if (ApplicationClass.skinOn == false) {
            binding.homeRecommend.setImageResource(R.drawable.starhouse_copyrighted)
            binding.homeRanking.setImageResource(R.drawable.squidhouse_copyrighted)
            binding.homeMypage.setImageResource(R.drawable.pineapplehouse_copyrighted)

            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.fragmentHomeConstraintLayout)
            constraintSet.connect(
                binding.homeRecommend.id,
                ConstraintSet.BOTTOM,
                binding.guidelineHorizontal4.id,
                ConstraintSet.TOP,
                0,
            )
            constraintSet.applyTo(binding.fragmentHomeConstraintLayout)
        }
    }

    private fun setupInitSettings() {
        coralGenerator.generateCoral()

        binding.apply {
            fragmentHomeScrollView.post {
                fragmentHomeScrollView.scrollTo(
                    fragmentHomeScrollView.getChildAt(
                        0,
                    ).width / 3,
                    0,
                )
            }
            homeRecommend.setOnClickListener {
                with(requireActivity() as MainActivity) {
                    callTransition()
                    delayClickWhileAnimation()
                }
                findNavController().navigate(R.id.action_navigation_home_to_navigation_recommend)
            }

            homeRanking.setOnClickListener {
                with(requireActivity() as MainActivity) {
                    callTransition()
                    delayClickWhileAnimation()
                }
                findNavController().navigate(R.id.action_navigation_home_to_navigation_ranking)
            }

            homeMypage.setOnClickListener {
                with(requireActivity() as MainActivity) {
                    callTransition()
                    delayClickWhileAnimation()
                }
                findNavController().navigate(R.id.action_navigation_home_to_navigation_status)
            }
        }
    }
}
