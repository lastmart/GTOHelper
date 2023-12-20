package com.gtohelper.presentation.ui.util

interface OnItemLongClickListener<T> {

    fun onItemLongClicked(item: T): Boolean
}