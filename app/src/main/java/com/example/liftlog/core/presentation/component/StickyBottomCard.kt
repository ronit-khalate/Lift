package com.example.liftlog.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.liftlog.ui.theme.blue
import com.example.liftlog.ui.theme.neutral
import com.example.liftlog.ui.theme.primary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StickyBottomCard(
    modifier: Modifier = Modifier,
    routineName:String,
    onCardClick:()->Unit,
    onFinishBtnClick:()->Unit
) {



        Card(
            modifier = Modifier
                .padding( bottom = 50.dp)
                .fillMaxWidth()
                .height(36.dp)
                .clickable { onCardClick() }


        ) {


            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = neutral),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {





                Text(
                    modifier = Modifier
                        .padding(start = 20.dp),
                    text = routineName,
                    color = primary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )

                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .height(28.dp)
                        .width(85.dp)
                        .clickable {onFinishBtnClick()},
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

}

@Preview
@Composable
private fun Preview() {

    StickyBottomCard(routineName = "Chest & Tripce", onFinishBtnClick = {}, onCardClick = {})
}