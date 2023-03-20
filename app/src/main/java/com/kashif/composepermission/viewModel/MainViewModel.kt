package com.kashif.composepermission.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
/**
 * Created by Mohammad Kashif Ansari on 21,March,2023
 */
class MainViewModel : ViewModel(){

    // FIFO
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun  dismissDialog()
    {
        //remove last entry in the queue
        visiblePermissionDialogQueue.removeFirst()
    }
    fun onPermissionResult(permission:String,isGranted:Boolean)
    {
        if(isGranted && !visiblePermissionDialogQueue.contains(permission))
            visiblePermissionDialogQueue.add(permission)
    }
}