package com.d204.algo.ui.status

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.d204.algo.ApplicationClass
import com.d204.algo.MainActivity
import com.d204.algo.R
import com.d204.algo.base.BaseFragment
import com.d204.algo.base.BaseViewModel
import com.d204.algo.data.model.Problem
import com.d204.algo.databinding.FragmentStatusBinding
import com.d204.algo.databinding.ItemStatusListBinding
import com.d204.algo.presentation.utils.Constants
import com.d204.algo.presentation.viewmodel.LikeProblemsUIModel
import com.d204.algo.presentation.viewmodel.StatusFragmentViewModel
import com.d204.algo.presentation.viewmodel.StatusUIModel
import com.d204.algo.ui.adapter.StatusAdapter
import com.d204.algo.ui.extension.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "Algo_StatusFragment"

@AndroidEntryPoint
class StatusFragment : BaseFragment<FragmentStatusBinding, BaseViewModel>() {
    companion object {
        const val PROBLEM_ID = "problemId"
        const val PROBLEM_TITLE = "problemTitle"
        const val PROBLEM_NUMBER = "problemNumber"
        const val PROBLEM_LEVEL = "problemLevel"
        const val PROBLEM_MEMO = "problemMemo"

        @JvmStatic
        fun newInstance(
            problemId: Long,
            problemTitle: String,
            problemNumber: Int,
            problemLevel: Int,
            problemMemo: String,
        ) =
            StatusFragment().apply {
                arguments = Bundle().apply {
                    putLong(PROBLEM_ID, problemId)
                    putString(PROBLEM_TITLE, problemTitle)
                    putInt(PROBLEM_NUMBER, problemNumber)
                    putInt(PROBLEM_LEVEL, problemLevel)
                    putString(PROBLEM_MEMO, problemMemo)
                }
            }
    }

    private val espHelper = ApplicationClass.preferencesHelper

    override fun getViewBinding(): FragmentStatusBinding =
        FragmentStatusBinding.inflate(layoutInflater)

    override val viewModel: StatusFragmentViewModel by viewModels()

    @Inject
    lateinit var statusAdapter: StatusAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        test()
        init()
    }

    // 테스트용 함수, 데이터 연결 후 제거
    private fun test() = with(binding) {
        initViewPager()
        statusRadarChartView
            .setDataList(
                arrayListOf(
                    RadarChartData(CharacteristicType.WISDOM, 93),
                    RadarChartData(CharacteristicType.VITALITY, 73),
                    RadarChartData(CharacteristicType.STRENGTH, 60),
                    RadarChartData(CharacteristicType.CHARISMA, 80),
                    RadarChartData(CharacteristicType.LUCK, 45),
                ),
            )
        statusChartCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                statusRadarChartView
                    .setSameLevelDataList(
                        arrayListOf(
                            RadarChartData(CharacteristicType.WISDOM, 50),
                            RadarChartData(
                                CharacteristicType.VITALITY,
                                50,
                            ),
                            RadarChartData(
                                CharacteristicType.STRENGTH,
                                50,
                            ),
                            RadarChartData(
                                CharacteristicType.CHARISMA,
                                50,
                            ),
                            RadarChartData(CharacteristicType.LUCK, 50),
                        ),
                    )
            } else {
                statusRadarChartView.setSameLevelDataList(arrayListOf())
            }
        }
    }

    private fun init() {
        initData()
        initViewPager()
    }

    private fun initData() = with(binding) {
        statusProfileUsername.text = ApplicationClass.preferencesHelper.prefUserNickname
        Glide.with(requireContext())
            .load(ApplicationClass.preferencesHelper.prefUserProfile)
            .into(statusProfileImg)

        // 티어표시
        val tierImg =
            if (ApplicationClass.skinOn) Constants.RANK_TIER[ApplicationClass.preferencesHelper.prefUserTier] else Constants.COPYRIGHT_RANK_TIER[ApplicationClass.preferencesHelper.prefUserTier]
        binding.statusRankImage.setImageResource(tierImg)

        // status 체인지
        observe(viewModel.statusData, ::onStatusChange)
        viewModel.getUserStatus(espHelper.prefUserId)

        observe(viewModel.statusAvgData, ::onAvgStatusChange)
        viewModel.getAvgStatus(espHelper.prefUserTier) // 평균값은 실시간 반영x -> 체크박스 체크하면 갱신

        // 좋아요한 문제 리스트 조회
        observe(viewModel.likeProblems, ::onViewStateChange)
//        viewModel.getLikeProblems(1)
        viewModel.getLikeProblems(ApplicationClass.preferencesHelper.prefUserId)
    }

    // 찜한 문제 리스트 초기화
    private fun initViewPager() = with(binding) {
        statusRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        statusRecyclerView.adapter = statusAdapter.apply {
            setStatusClickListener(object : StatusAdapter.StatusClickListener {
                override fun bookmarkClick(
                    binding: ItemStatusListBinding,
                    problem: Problem,
                    isChecked: Boolean,
                    position: Int,
                ) {
                    viewModel.postProblemLike(
                        problemId = problem.id,
//                        userId = 1,
                        userId = ApplicationClass.preferencesHelper.prefUserId,
                        problemLike = isChecked,
                    )
                }

                override fun memoClick(
                    binding: ItemStatusListBinding,
                    problem: Problem,
                    position: Int,
                ) {
                    Log.d(TAG, "memoClick: $problem")
                    val fragment = MemoFragment.newInstance(
                        problem.id,
                        problem.title,
                        problem.problemNumber,
                        problem.problemLevel,
                        problem.problemMemo ?: "",
                    )
                    findNavController().navigate(
                        R.id.action_navigation_status_to_navigation_memo,
                        fragment.arguments,
                    )
                }

                override fun layoutClick(problem: Problem) {
                    runStomp(problem.problemNumber.toString())
                }
            })
        }
    }

    // 좋아요한 문제 리스트 조회
    private fun onViewStateChange(result: LikeProblemsUIModel) = with(binding) {
        if (result.isRedelivered) return
        when (result) {
            is LikeProblemsUIModel.Error -> handleErrorMessage(result.error)
            LikeProblemsUIModel.Loading -> handleLoading(true)
            is LikeProblemsUIModel.Success -> {
                handleLoading(false)
                statusAdapter.list = result.data
            }
        }
    }

    private fun onStatusChange(result: StatusUIModel) = with(binding) {
        if (result.isRedelivered) return
        when (result) {
            is StatusUIModel.Error -> handleErrorMessage(result.error)
            StatusUIModel.Loading -> handleLoading(true)
            is StatusUIModel.Success -> {
                Log.d(TAG, "onStatusChange: ${result.data}")
                handleLoading(false)
                statusRadarChartView
                    .setDataList(
                        arrayListOf(
                            RadarChartData(CharacteristicType.WISDOM, result.data.wisdom),
                            RadarChartData(CharacteristicType.VITALITY, result.data.vitality),
                            RadarChartData(CharacteristicType.STRENGTH, result.data.strength),
                            RadarChartData(CharacteristicType.CHARISMA, result.data.charisma),
                            RadarChartData(CharacteristicType.LUCK, result.data.luck),
//                            RadarChartData(CharacteristicType.WISDOM, 80),
//                            RadarChartData(CharacteristicType.VITALITY, 90),
//                            RadarChartData(CharacteristicType.STRENGTH, 70),
//                            RadarChartData(CharacteristicType.CHARISMA, 60),
//                            RadarChartData(CharacteristicType.LUCK, 50),
                        ),
                    )
            }
        }
    }

    private fun onAvgStatusChange(result: StatusUIModel) = with(binding) {
        if (result.isRedelivered) return
        when (result) {
            is StatusUIModel.Error -> handleErrorMessage(result.error)
            StatusUIModel.Loading -> handleLoading(true)
            is StatusUIModel.Success -> {
                handleLoading(false)
                Log.d(TAG, "onAvgStatusChange: ${result.data}")
                statusChartCheckbox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        statusRadarChartView
                            .setSameLevelDataList(
                                arrayListOf(
                                    RadarChartData(CharacteristicType.WISDOM, result.data.wisdom),
                                    RadarChartData(CharacteristicType.VITALITY, result.data.vitality,),
                                    RadarChartData(CharacteristicType.STRENGTH, result.data.strength,),
                                    RadarChartData(CharacteristicType.CHARISMA, result.data.charisma,),
                                    RadarChartData(CharacteristicType.LUCK, result.data.luck),
//                                    RadarChartData(CharacteristicType.WISDOM, 30),
//                                    RadarChartData(CharacteristicType.VITALITY, 25),
//                                    RadarChartData(CharacteristicType.STRENGTH, 20),
//                                    RadarChartData(CharacteristicType.CHARISMA, 35),
//                                    RadarChartData(CharacteristicType.LUCK, 40),
                                ),
                            )
                    } else {
                        statusRadarChartView.setSameLevelDataList(arrayListOf())
                    }
                }
            }
        }
    }

    private fun runStomp(problemNum: String) {
        val targetUrl = "www.acmicpc.net/problem/$problemNum"
        (requireActivity() as MainActivity).sendSocketMessage(targetUrl)
    }
}
