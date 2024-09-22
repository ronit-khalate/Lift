package com.ronit.liftlog.core.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ronit.liftlog.ui.theme.black
import com.ronit.liftlog.ui.theme.body
import com.ronit.liftlog.ui.theme.neutral
import com.ronit.liftlog.ui.theme.primary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StickyBottomCard(
    modifier: Modifier = Modifier,
    routineName:String,
    totalExercise:Int,
    onCardClick:()->Unit,
    onFinishBtnClick:()->Unit
) {



        Card(
            modifier = modifier
                .padding(bottom = 10.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clickable { onCardClick() },
            colors = CardDefaults.cardColors(containerColor = neutral)


        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {




                Column(
                    modifier = Modifier
                        .wrapContentSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceAround
                ) {

                    Text(
                        text = routineName,
                        fontStyle = MaterialTheme.typography.titleSmall.fontStyle,
                        fontWeight = FontWeight.Bold,
                        color = primary,
                        fontSize = MaterialTheme.typography.titleSmall.fontSize

                    )

                    Text(
                        text = "$totalExercise exercises",
                        fontStyle = MaterialTheme.typography.labelSmall.fontStyle,
                        fontWeight = FontWeight.Normal,
                        color = body,
                        fontSize = MaterialTheme.typography.labelSmall.fontSize

                    )




                }

                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .height(40.dp)
                        .width(80.dp)
                        .clickable { onFinishBtnClick() },
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(containerColor = primary)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),


                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Finish",
                            fontStyle = MaterialTheme.typography.labelMedium.fontStyle,
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = black
                        )
                    }
                }
            }
        }

}

@Preview
@Composable
private fun Preview() {

    StickyBottomCard(routineName = "Chest & Tripce", totalExercise = 6, onFinishBtnClick = {}, onCardClick = {})
}