package com.d204.algo.presentation.viewmodel

import com.d204.algo.base.BaseViewModel
import com.d204.algo.presentation.utils.CoroutineContextProvider
import com.d204.algo.presentation.utils.ExceptionHandler
import com.d204.algo.presentation.utils.PresentationPreferencesHelper
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
) : BaseViewModel(contextProvider) {
    private var skinOn = false
    override val coroutineExceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
        // _character.postValue(CharacterDetailUIModel.Error(exception.message ?: "Error"))
    }
}
