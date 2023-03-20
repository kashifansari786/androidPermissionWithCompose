 package com.kashif.composepermission

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
                Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { cameraPermissionResultLauncher.launch(
                        Manifest.permission.CAMERA
                    )}) {
                        Text(text = "Request one Permission")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Request multiple Permission")
                    }
                }
            }
        }
    }
}
