package com.gtohelper.presentation.components.forms

sealed class FormState{
    data object FormEditingState : FormState()
    data object FormSubmittingState : FormState()
    data object FormSubmittedState : FormState()
    data class FormSubmissionFailedState(val error: String) :
        FormState()
}
