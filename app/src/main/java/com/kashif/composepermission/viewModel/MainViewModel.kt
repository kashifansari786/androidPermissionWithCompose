package com.kashif.composepermission.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel :ViewModel(){

    // FIFO
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun  dismissDialog()
    {
        //remove last entry in the queue
        visiblePermissionDialogQueue.removeLast()
    }
    fun onPermissionResult(permission:String,isGranted:Boolean)
    {
        if(isGranted)
            visiblePermissionDialogQueue.add(0,permission)
    }
}