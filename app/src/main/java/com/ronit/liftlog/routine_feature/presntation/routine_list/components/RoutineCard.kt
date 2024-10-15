package com.ronit.liftlog.routine_feature.presntation.routine_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ronit.liftlog.core.presentation.component.MaxWidthButton
import com.ronit.liftlog.ui.theme.neutral
import com.ronit.liftlog.ui.theme.body
import com.ronit.liftlog.ui.theme.primaryText


@Composable
fun RoutineCard(
    routineName:String,
    onCardClick:()->Unit,
    onStartNowClick:()->Unit,
    exerciseCount:Int
) {



    Card(
        onClick = onCardClick,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = neutral)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 15.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {

            Column(
                modifier = Modifier.wrapContentSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    text = routineName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = primaryText,

                )
                
                Text(
                    text = "$exerciseCount exercises",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Normal,
                    color = body,

                )




            }


            Spacer(Modifier.height(8.dp))

           MaxWidthButton(onClick = onStartNowClick, text = "Start Now")
        }
    }



}


@Preview(
    showBackground = true
)
@Composable
private fun ClickableRowPreview() {

    val bg = MaterialTheme.colorScheme.onPrimary
    RoutineCard(
        exerciseCount = 30,
       routineName =  "Ronit",
        onCardClick = {},
        onStartNowClick = {}
    )
}