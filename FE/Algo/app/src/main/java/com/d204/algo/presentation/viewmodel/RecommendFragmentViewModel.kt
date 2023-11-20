package com.d204.algo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import com.d204.algo.base.BaseViewModel
import com.d204.algo.data.model.Problem
import com.d204.algo.data.repository.ProblemRepository
import com.d204.algo.presentation.utils.CoroutineContextProvider
import com.d204.algo.presentation.utils.ExceptionHandler
import com.d204.algo.presentation.utils.UiAwareLiveData
import com.d204.algo.presentation.utils.UiAwareModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

sealed class RecommendUIModel : UiAwareModel() {
    object Loading : RecommendUIModel()
    data class Error(var error: String = "") : RecommendUIModel()
    data class Success(val data: List<Problem>) : RecommendUIModel()
}

private const val TAG = "RecommendFragmentViewModel"

@HiltViewModel
class RecommendFragmentViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val problemRepository: ProblemRepository, // -> Module에 @provide로 impl return 하는 함수 있어야함
) : BaseViewModel(contextProvider) {
    // 어떤 UIModel에서 에러가 났는지 표시하기 위해 사용
    private var errorSite = 0
    private var isPostingBookMark = false

    // <!------------------------------------------------------->
    override val coroutineExceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
        Log.d(TAG, "$errorSite ")
        when (errorSite) {
            1 -> _strongList.postValue(RecommendUIModel.Error(exception.message ?: "Error"))
            2 -> _weakList.postValue(RecommendUIModel.Error(exception.message ?: "Error"))
            3 -> _similarList.postValue(RecommendUIModel.Error(exception.message ?: "Error"))
            else -> Log.d(TAG, "예기치 못한 에러 : $message")
        }
    }

    private val _selectedStrongList = UiAwareLiveData<RecommendUIModel>()
    var selectedStrongList: LiveData<RecommendUIModel> = _selectedStrongList

    private val _selectedWeakList = UiAwareLiveData<RecommendUIModel>()
    var selectedWeakList: LiveData<RecommendUIModel> = _selectedWeakList

    private val _selectedSimilarList = UiAwareLiveData<RecommendUIModel>()
    var selectedSimilarList: LiveData<RecommendUIModel> = _selectedSimilarList

    // <!------------------------------------------------------->
    private val _strongList = UiAwareLiveData<RecommendUIModel>()
    var strongList: LiveData<RecommendUIModel> = _strongList

    private var constStrongList: List<Problem> = listOf()

    private val _weakList = UiAwareLiveData<RecommendUIModel>()
    var weakList: LiveData<RecommendUIModel> = _weakList

    private var constWeakList: List<Problem> = listOf()

    private val _similarList = UiAwareLiveData<RecommendUIModel>()
    var similarList: LiveData<RecommendUIModel> = _similarList

    private var constSimilarList: List<Problem> = listOf()

    fun postProblemLike(problemId: Long, userId: Long, problemLike: Boolean) {
        if (isPostingBookMark) return
        isPostingBookMark = true
        launchCoroutineIO {
            problemRepository.postLikeProblems(Problem(problemId, userId, problemLike)).collect {
                isPostingBookMark = false
            }
        }
    }

    fun getStrongs(): LiveData<RecommendUIModel> {
        return strongList
    }

    fun getWeaks(): LiveData<RecommendUIModel> {
        return weakList
    }

    fun getSimilars(): LiveData<RecommendUIModel> {
        return similarList
    }

    // <!------------------------------------------------------->
    fun setConstStrongs(list: List<Problem>) {
        constStrongList = list
        loadConstStrongList()
    }

    fun setConstWeaks(list: List<Problem>) {
        constWeakList = list
        loadConstWeakList()
    }

    fun setConstSimilars(list: List<Problem>) {
        constSimilarList = list
        loadConstSimilarList()
    }

    // <!------------------------------------------------------->
    fun getSelectedStrongs(): LiveData<RecommendUIModel> {
        return selectedStrongList
    }

    fun getSelectedWeaks(): LiveData<RecommendUIModel> {
        return selectedWeakList
    }

    fun getSelectedSimilars(): LiveData<RecommendUIModel> {
        return selectedSimilarList
    }

    // <!------------------------------------------------------->
    fun getStrongList(userId: Long) {
        errorSite = 1
        _strongList.postValue(RecommendUIModel.Loading)
        launchCoroutineIO {
            loadStrongList(userId)
        }
    }

    private suspend fun loadStrongList(userId: Long) {
        problemRepository.getStrongProblems(userId).collect {
            Log.d(TAG, "loadStrongList: $it")
            _strongList.postValue(RecommendUIModel.Success(it))
        }
    }

    fun loadConstStrongList() {
        _selectedStrongList.postValue(RecommendUIModel.Success(constStrongList.shuffled().take(3)))
    }

    fun getWeakList(userId: Long) {
        errorSite = 2
        _weakList.postValue(RecommendUIModel.Loading)
        launchCoroutineIO {
            loadWeakList(userId)
        }
    }

    private suspend fun loadWeakList(userId: Long) {
        problemRepository.getWeakProblems(userId).collect {
            _weakList.postValue(RecommendUIModel.Success(it))
        }
    }

    fun loadConstWeakList() {
        _selectedWeakList.postValue(RecommendUIModel.Success(constWeakList.shuffled().take(3)))
    }

    fun getSimilarList(userId: Long) {
        errorSite = 3
        _similarList.postValue(RecommendUIModel.Loading)
        launchCoroutineIO {
            loadSimilarList(userId)
        }
    }

    private suspend fun loadSimilarList(userId: Long) {
        problemRepository.getSimilarProblems(userId).collect {
            _similarList.postValue(RecommendUIModel.Success(it))
        }
    }

    fun loadConstSimilarList() {
        _selectedSimilarList.postValue(RecommendUIModel.Success(constSimilarList.shuffled().take(3)))
    }
}
