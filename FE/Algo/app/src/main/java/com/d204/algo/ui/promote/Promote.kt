package com.d204.algo.ui.promote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.d204.algo.base.BaseFragment
import com.d204.algo.base.BaseViewModel
import com.d204.algo.databinding.FragmentPromoteBinding
import com.d204.algo.presentation.viewmodel.PromoteFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PromoteFragment : BaseFragment<FragmentPromoteBinding, BaseViewModel>() {
    companion object {
        @JvmStatic
        fun newInstance() =
            PromoteFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun getViewBinding(): FragmentPromoteBinding = FragmentPromoteBinding.inflate(layoutInflater)
    override val viewModel: PromoteFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }
}