package com.d204.algo.ui.status

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.d204.algo.ApplicationClass
import com.d204.algo.base.BaseFragment
import com.d204.algo.base.BaseViewModel
import com.d204.algo.databinding.FragmentMemoBinding
import com.d204.algo.presentation.utils.Constants
import com.d204.algo.presentation.viewmodel.MemoFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "Algo_MemoFragment"

@AndroidEntryPoint
class MemoFragment : BaseFragment<FragmentMemoBinding, BaseViewModel>() {
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
            MemoFragment().apply {
                arguments = Bundle().apply {
                    putLong(PROBLEM_ID, problemId)
                    putString(PROBLEM_TITLE, problemTitle)
                    putInt(PROBLEM_NUMBER, problemNumber)
                    putInt(PROBLEM_LEVEL, problemLevel)
                    putString(PROBLEM_MEMO, problemMemo)
                }
            }
    }

    override fun getViewBinding(): FragmentMemoBinding = FragmentMemoBinding.inflate(layoutInflater)
    override val viewModel: MemoFragmentViewModel by viewModels()
    private lateinit var imm: InputMethodManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding) {
        initData()
        imm = (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        // MemoFragment로 들어왔을 때 입력란 focus 및 키보드 띄우기
        memoEditText.requestFocus()
        imm.showSoftInput(memoEditText, 0)

        // 뒤로가기
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG, "handleOnBackPressed: 콜백변경")
//                    if (memoEditText.hasFocus()) {
//                        memoEditText.clearFocus()
//                    } else {
                    findNavController().navigateUp()
//                    }
                }
            },
        )

        // 등록 버튼
        memoRegisterButton.setOnClickListener {
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
            registerMemo()
        }
    }

    private fun initData() = with(binding) {
        memoProblemNumberTextView.text = requireArguments().getInt(StatusFragment.PROBLEM_NUMBER).toString()
        memoTitleTextView.text = requireArguments().getString(StatusFragment.PROBLEM_TITLE)
        memoTierImageView.setImageResource(Constants.TIER[requireArguments().getInt(StatusFragment.PROBLEM_LEVEL)])
        memoEditText.setText(requireArguments().getString(StatusFragment.PROBLEM_MEMO))
        memoTitleTextView.isSelected = true
    }

    // 메모 등록 버튼 클릭
    private fun registerMemo() {
        viewModel.updateMemo(
            requireArguments().getLong(StatusFragment.PROBLEM_ID),
//            1,
            if (ApplicationClass.preferencesHelper.prefUserId == 0L) 1 else ApplicationClass.preferencesHelper.prefUserId,
            binding.memoEditText.text.toString(),
        )
    }
}
