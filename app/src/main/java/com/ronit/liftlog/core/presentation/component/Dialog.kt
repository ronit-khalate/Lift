package com.ronit.liftlog.core.presentation.component

import android.graphics.drawable.Icon
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.ronit.liftlog.ui.theme.black
import com.ronit.liftlog.ui.theme.neutral
import com.ronit.liftlog.ui.theme.primary
import com.ronit.liftlog.ui.theme.primaryText

@Composable
fun BasicDialog(
    modifier: Modifier = Modifier,
    onDismiss:()->Unit,
    onConfirm:()->Unit,
    dismissBtnText:String?,
    confirmBtnText:String?,
    icon: ImageVector?=null,
    titleText:String,
    textString:String,
    confirmBtnColor: Color = primaryText,
    dismissBtnColor: Color = primaryText

) {



    AlertDialog(

        onDismissRequest = onDismiss,
        dismissButton = {
            dismissBtnText?.let {
                TextButton(
                    onClick ={onDismiss()},
                ) {
                    Text(
                        text =dismissBtnText,
                        color = dismissBtnColor
                    )
                }
            }

        },
        confirmButton = {
            confirmBtnText?.let {
                TextButton(onClick ={onConfirm() }) {
                    Text(
                        text =confirmBtnText,
                        color = confirmBtnColor
                    )
                }
            }

        },

        icon = {
            icon?.let {
                Icon(imageVector = icon, contentDescription = null )
            }

        },
        containerColor = neutral,
        title = {
            Text(
                text = titleText
            )
        },
        text = {
            Text(
                color = primaryText,
                text = textString
            )
        },
        titleContentColor = primaryText,
        textContentColor = primaryText,



    )

}



data class DialogContent(
    val icon: Icon?=null,
    val title:String,
    val text:String,
    val dismissBtnText:String?=null,
    val confirmBtnText:String?=null,
    val onDismiss:(()->Unit)?=null,
    val onConfirm:(()->Unit)? = null
)


sealed interface DialogEvent{

    data class OnConfirmButtonClicked(val onConfirm:()->Unit):DialogEvent
    data class OnDismissButtonClicked(val onDismiss:()->Unit):DialogEvent
}

@Preview
@Composable
private fun AlertDialogPreview() {

    BasicDialog(
        onDismiss = { /*TODO*/ },
        onConfirm = {},
        titleText = "Choose Muscle Group",
        textString = "Please Choose Group",
        confirmBtnText = "Ok",
        dismissBtnText = "ok"

    )
}