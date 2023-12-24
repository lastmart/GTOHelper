package com.gtohelper.presentation.components.forms

sealed class FormState<T>(open val form: T)

interface EditableState
data class FormEditingState<T>(override val form: T) : FormState<T>(form), EditableState
data class FormSubmittingState<T>(override val form: T) : FormState<T>(form)
data class FormSubmittedState<T>(override val form: T) : FormState<T>(form), EditableState
data class FormSubmissionFailedState<T>(override val form: T, val error: String) :
    FormState<T>(form), EditableState