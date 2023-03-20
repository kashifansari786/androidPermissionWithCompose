package com.kashif.composepermission



import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Created by Mohammad Kashif Ansari on 21,March,2023
 */
@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentDecline:Boolean,
    onDismiss: ()->Unit,
    onOkClick:()->Unit,
    onGoToAppSettingClick:()->Unit,
    modifier: Modifier = Modifier
)
{
    AlertDialog(onDismissRequest = onDismiss,
    buttons = {
        Column(modifier = Modifier.fillMaxWidth()) {
            Divider()
            Text(
                text = if (isPermanentDecline) {
                    "Grant Permission"
                }else{
                    "Ok"
                },
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (isPermanentDecline)
                            onGoToAppSettingClick()
                        else
                            onOkClick()
                    }
                    .padding(16.dp)
            )
        }
    }, title = {
            Text(text = "Permission required")
        }, text = {
            Text(text = permissionTextProvider.getDescription(
                isPermanentDecline=isPermanentDecline
            ))
        }, modifier = modifier)
}

interface PermissionTextProvider{
    fun getDescription(isPermanentDecline: Boolean):String
}

class CameraPermissionTextProvider:PermissionTextProvider{
    override fun getDescription(isPermanentDecline: Boolean): String {
        return if(isPermanentDecline){
            "It seems you permanently decline camera permission"+
                    "You can go to the app setting to grant it."
        }else
        {
            "This app needs access to you camera so the your friends"+
                    "can see you in a call."
        }
    }

}
class RecordPermissionTextProvider:PermissionTextProvider{
    override fun getDescription(isPermanentDecline: Boolean): String {
        return if(isPermanentDecline){
            "It seems you permanently decline microphone permission"+
                    "You can go to the app setting to grant it."
        }else
        {
            "This app needs access to you microphone so the your friends"+
                    "can hear you in a call."
        }
    }

}
class PhoneCallPermissionTextProvider:PermissionTextProvider{
    override fun getDescription(isPermanentDecline: Boolean): String {
        return if(isPermanentDecline){
            "It seems you permanently decline phone caling permission"+
                    "You can go to the app setting to grant it."
        }else
        {
            "This app needs phone calling permission so that you can talk"+
                    "to your friends."
        }
    }

}