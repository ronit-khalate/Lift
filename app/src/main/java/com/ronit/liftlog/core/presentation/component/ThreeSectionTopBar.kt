package com.ronit.liftlog.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//Topbar
@Composable
fun ThreeSectionTopBar(
    modifier: Modifier = Modifier,
    leftContent:(@Composable ()->Unit)={},
    middleContent:(@Composable ()->Unit)={},
    rightContent:(@Composable ()->Unit)={},
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){


        leftContent()
        middleContent()
        rightContent()



    }

}

@Preview(showBackground = true)
@Composable
fun ThreeSectionTopBarPreview() {
    ThreeSectionTopBar(
        modifier = Modifier
           ,

        leftContent = {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Edit")
            }
        },

        rightContent = {
            IconButton(onClick = { /*TODO*/ }) {
                Image(imageVector = Icons.Default.Add, contentDescription = "Add Routine")
            }
        }
    )

}
