package com.example.liftlog.start_routine_feature.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.liftlog.ui.theme.blue
import com.example.liftlog.ui.theme.primary


@Composable
fun StartRoutineScreenTopBar(
    modifier: Modifier = Modifier,
    onBackNavigate:()->Unit,
    onFinishBtnClick:()->Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = onBackNavigate) {

            Image(
                colorFilter = ColorFilter.tint(color = primary),
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                contentDescription = "Navigate Back"
            )
            
        }


        Card(
            modifier = Modifier
                .height(28.dp)
                .width(85.dp)
                .clickable { onFinishBtnClick() },
            colors = CardDefaults.cardColors(containerColor = blue)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Finish",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = primary
                )
            }
        }

    }
    
}

@Preview
@Composable
private fun StartRoutineScreenTopBarPreview() {
    StartRoutineScreenTopBar(onBackNavigate = {}, onFinishBtnClick = {})
}