package com.gtohelper.presentation.components.forms

typealias ValidationError = String

interface AppForm {
    fun validate() : ValidationError?
}