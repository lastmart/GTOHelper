package com.gtohelper.presentation.ui.util

import androidx.fragment.app.Fragment
import com.gtohelper.data.database.AppDatabase
import com.gtohelper.presentation.ui.main.MainActivity

val Fragment.appDatabase: AppDatabase
    get() = (requireActivity() as MainActivity).database