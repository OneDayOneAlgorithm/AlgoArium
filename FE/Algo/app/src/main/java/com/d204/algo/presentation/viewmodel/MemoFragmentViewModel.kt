package com.d204.algo.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.d204.algo.base.BaseViewModel
import com.d204.algo.data.model.Problem
import com.d204.algo.data.repository.StatusRepository
import com.d204.algo.presentation.utils.CoroutineContextProvider
import com.d204.algo.presentation.utils.ExceptionHandler
import com.d204.algo.presentation.utils.UiAwareLiveData
import com.d204.algo.presentation.utils.UiAwareModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

sealed class MemoUIModel : UiAwareModel() {
    object Loading : MemoUIModel()
    data class Error(var error: String = "") : MemoUIModel()
    data class Success(val data: Unit) : MemoUIModel()
}

@HiltViewModel
class MemoFragmentViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    // private val userRepository: UserRepository // -> Module에 @provide로 impl return 하는 함수 있어야함
    private val statusRepository: StatusRepository,
) : BaseViewModel(contextProvider) {
    override val coroutineExceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
        // _character.postValue(CharacterDetailUIModel.Error(exception.message ?: "Error"))
    }

    private val _updateMemoResult = UiAwareLiveData<MemoUIModel>()
    var updateMemoResult: LiveData<MemoUIModel> = _updateMemoResult

    fun updateMemo(problemId: Long, userId: Long, problemMemo: String) {
        launchCoroutineIO {
            statusRepository.updateMemo(Problem(problemId, userId, problemMemo)).collect {
                _updateMemoResult.postValue(MemoUIModel.Success(it))
            }
        }
    }
}
