package com.d204.algo.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.d204.algo.ui.extension.dismissLoadingDialog
import com.d204.algo.ui.extension.showLoadingDialog
import com.d204.algo.ui.extension.showSnackBar

private const val TAG = "BaseFragment"
abstract class BaseFragment<VB : ViewBinding, ViewModel : BaseViewModel> : Fragment() {

    protected lateinit var binding: VB
    protected abstract val viewModel: ViewModel

    // fragment에서 쓰는 생성자 함수를 상속하고 싶었는데 쉽지않네.. 어떻게 방법 없나?
//    companion object {
//        // BaseFragment를 상속받는 SubClass의 생성자를 받아서 instance에서 호출
//        // subInstance()를 구현하지 않으면 null이므로 이를 방지하기 위해 아예 에러를 띄우게 만듬(companion은 abstract 불가능)
//        fun subInstance(): BaseFragment<*, *> {
//            throw IllegalStateException("subInstance must be overridden in the subclass")
//        }
//
//        fun <T : Parcelable> instance(list: List<T>, vararg argName: String): BaseFragment<*, *> {
//            val fragment = subInstance()
//            val args = Bundle()
//
//            for (i in argName.indices) {
//                args.putParcelableArrayList(argName[i], ArrayList(list))
//            }
//
//            fragment.arguments = args
//            return fragment
//        }
//    }

    abstract fun getViewBinding(): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = getViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerEvents()
    }

    private fun observerEvents() {
        viewModel.apply {
            /*isLoading.observe(viewLifecycleOwner, {
                handleLoading(it == true)
            })
            errorMessage.observe(viewLifecycleOwner, {
                handleErrorMessage(it)
            })
            noInternetConnectionEvent.observe(viewLifecycleOwner, {
                handleErrorMessage(getString(R.string.no_internet_connection))
            })
            connectTimeoutEvent.observe(viewLifecycleOwner, {
                handleErrorMessage(getString(R.string.connect_timeout))
            })
            forceUpdateAppEvent.observe(viewLifecycleOwner, {
                handleErrorMessage(getString(R.string.force_update_app))
            })
            serverMaintainEvent.observe(viewLifecycleOwner, {
                handleErrorMessage(getString(R.string.server_maintain_message))
            })
            unknownErrorEvent.observe(viewLifecycleOwner, {
                handleErrorMessage(getString(R.string.unknown_error))
            })*/
        }
    }
    protected open fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoadingDialog() else dismissLoadingDialog()
    }

    protected open fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return
        dismissLoadingDialog()
        Log.d(TAG, "handleErrorMessage")
        showSnackBar(binding.root, message)
    }
}
