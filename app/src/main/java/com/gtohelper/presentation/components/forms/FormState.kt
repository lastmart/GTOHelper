package com.gtohelper.presentation.components.forms

sealed class FormState<T : AppForm>(open val form: T)

interface EditableState

data class FormPreparationState<T : AppForm>(override val form: T) : FormState<T>(form)
data class FormPreparationFailedState<T : AppForm>(override val form: T) : FormState<T>(form)
data class FormEditingState<T : AppForm>(override val form: T) : FormState<T>(form), EditableState
data class FormSubmittingState<T : AppForm>(override val form: T) : FormState<T>(form)
data class FormSubmittedState<T : AppForm>(override val form: T) : FormState<T>(form), EditableState
data class FormSubmissionFailedState<T : AppForm>(override val form: T, val error: String) :
    FormState<T>(form), EditableState