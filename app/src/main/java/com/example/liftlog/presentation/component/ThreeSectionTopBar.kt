package com.example.liftlog.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
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
            .height(55.dp)
            .padding(start = 10.dp, end = 10.dp),

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
