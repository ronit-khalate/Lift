package com.example.liftlog.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ClickableRow(
    height:Int,
    text:String,
    onClick:()->Unit,
    leftIcon: @Composable ()-> Unit
) {


    Row(
        modifier = Modifier
            .height(height.dp)
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row (
            modifier = Modifier
                .weight(8f)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            Text(

                text = text,
                fontSize = 18.sp
            )

        }
        Row (
            modifier = Modifier
                .weight(2f)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ){
            leftIcon()
        }

    }

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth(),
        thickness = 1.dp
    )

}


@Preview(
    showBackground = true
)
@Composable
private fun ClickableRowPreview() {
    ClickableRow(
        30,
        "Ronit",
        {},
        {
            Image(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
        }
    )
}