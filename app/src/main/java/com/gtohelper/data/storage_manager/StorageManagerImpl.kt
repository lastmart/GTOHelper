package com.gtohelper.data.storage_manager

import android.os.Environment
import com.gtohelper.domain.storage_manager.StorageManager


class StorageManagerImpl : StorageManager {

    override fun saveFile() {

        if(isExternalStorageAvailable() && !isExternalStorageReadOnly()){
            println("Can save file")
        }

    }


    private fun isExternalStorageReadOnly(): Boolean {
        val externalStorageState = Environment.getExternalStorageState()
        return externalStorageState == Environment.MEDIA_MOUNTED_READ_ONLY
    }

    private fun isExternalStorageAvailable(): Boolean {
        val externalStorageState = Environment.getExternalStorageState()
        return externalStorageState == Environment.MEDIA_MOUNTED
    }
}