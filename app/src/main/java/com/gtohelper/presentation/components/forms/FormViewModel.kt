package com.gtohelper.presentation.components.forms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class FormViewModel<T : AppForm>(
    initialForm: T,
) : ViewModel() {

    protected val mutableState = MutableStateFlow<FormState<T>>(FormPreparationState(initialForm))
    protected val form
        get() = mutableState.value.form

    val state = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            mutableState.update { FormPreparationState(form) }
            prepareData()
            mutableState.update { FormEditingState(form) }
        }
    }

    protected open suspend fun prepareData() = Unit

    protected abstract suspend fun sendData()

    fun submit() {
        viewModelScope.launch {
            mutableState.update { FormSubmittingState(form) }
            val error = form.validate()
            delay(2000)

            if (error != null) {
                mutableState.update { FormSubmissionFailedState(form, error) }
            } else {
                sendData()
                mutableState.update { FormSubmittedState(form) }
            }
        }
    }
}