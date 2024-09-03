package com.example.liftlog.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ClickableRow(
    height:Int,
    text:String,
    onClick:()->Unit,
    backGroundColor: Color= Color.Transparent,
    leftIcon: @Composable ()-> Unit
) {


    Row(
        modifier = Modifier
            .height(height.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .background(backGroundColor),
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



}


@Preview(
    showBackground = true
)
@Composable
private fun ClickableRowPreview() {

    val bg = MaterialTheme.colorScheme.onPrimary
    ClickableRow(
        30,
        "Ronit",
        {},
        backGroundColor = bg,
        {
            Image(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
        }
    )
}