 package com.kashif.composepermission

 /**
  * Created by Mohammad Kashif Ansari on 21,March,2023
  */

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kashif.composepermission.ui.theme.PermissionsGuideComposeTheme
import com.kashif.composepermission.viewModel.MainViewModel
import kotlin.contracts.contract

 class MainActivity : ComponentActivity() {
     private val permissionToRequest = arrayOf(
         Manifest.permission.RECORD_AUDIO,
         Manifest.permission.CALL_PHONE
     )
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PermissionsGuideComposeTheme {
               val viewModel = viewModel<MainViewModel>()
                val dialogQueue=viewModel.visiblePermissionDialogQueue

                val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = {isGranted ->
                        viewModel.onPermissionResult(
                            permission = Manifest.permission.CAMERA,
                            isGranted=isGranted
                        )

                    }
                )
                val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = {permis ->
                        permissionToRequest.forEach{permission->
                        viewModel.onPermissionResult(
                            permission = Manifest.permission.CAMERA,
                            isGranted=permis[permission]==true
                        )}


                    }
                )
                Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { cameraPermissionResultLauncher.launch(
                        Manifest.permission.CAMERA
                    )}) {
                        Text(text = "Request one Permission")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        multiplePermissionResultLauncher.launch(permissionToRequest)
                         }) {
                        Text(text = "Request multiple Permission")
                    }
                }
                dialogQueue
                    .reversed()
                    .forEach{permission->
                PermissionDialog(
                    permissionTextProvider = when(permission){
                                                Manifest.permission.CAMERA-> {
                                                    CameraPermissionTextProvider()
                                                }
                        Manifest.permission.RECORD_AUDIO-> {
                            RecordPermissionTextProvider()
                        }
                        Manifest.permission.CALL_PHONE-> {
                            PhoneCallPermissionTextProvider()
                        }
                        else-> return@forEach
                        },
                    isPermanentDecline = !shouldShowRequestPermissionRationale(
                        permission
                    ),
                    onDismiss = { viewModel::dismissDialog },
                    onOkClick = { viewModel.dismissDialog()
                                multiplePermissionResultLauncher.launch(
                                    arrayOf(permission)
                                )},
                    onGoToAppSettingClick = ::openAppSetting)}
            }
        }
    }
}

 fun Activity.openAppSetting(){
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
    Uri.fromParts("package",packageName,null)).also(::startActivity)
 }
