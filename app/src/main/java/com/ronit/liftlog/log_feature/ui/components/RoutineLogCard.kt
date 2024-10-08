package com.ronit.liftlog.log_feature.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ronit.liftlog.core.data.model.Exercise
import com.ronit.liftlog.core.data.model.ExerciseLog
import com.ronit.liftlog.core.data.model.Log
import com.ronit.liftlog.core.data.model.Routine
import com.ronit.liftlog.core.data.model.Set
import com.ronit.liftlog.ui.theme.body
import com.ronit.liftlog.ui.theme.neutral
import com.ronit.liftlog.ui.theme.primaryText
import io.realm.kotlin.ext.realmListOf

@Composable
fun RoutineLogCard(
    modifier: Modifier = Modifier,
    log: Log,
    onClick:(routineId:String?)->Unit
) {

        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClick(log.routineId?.toHexString()) },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = neutral)
        ) {

            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(

                        text = log.routineName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = primaryText
                    )


                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                ) {


                    for (exerciseLog in log.exercisesLog){

                        Text(
                            modifier = Modifier.padding(vertical = 2.dp),
                            text = "${exerciseLog.setList.size} x ${exerciseLog.exercise?.name}",
                            color = primaryText,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
}


@Preview
@Composable
private fun RoutineLogCardPreview() {

    RoutineLogCard(
        log = Log().apply {

            this.routineName = "Chest"
            this.exercisesLog = realmListOf(

                ExerciseLog().apply {
                    this.exercise = Exercise()
                        .apply {
                            this.name ="Bench Press"
                        }

                    this.setList = realmListOf(Set(),Set())
                },
                ExerciseLog().apply {
                    this.exercise = Exercise()
                        .apply {
                            this.name ="Bench Press"
                        }

                    this.setList = realmListOf(Set(),Set())
                },
                ExerciseLog().apply {
                    this.exercise = Exercise()
                        .apply {
                            this.name ="Bench Press"
                        }

                    this.setList = realmListOf(Set(),Set())
                },
                ExerciseLog().apply {
                    this.exercise = Exercise()
                        .apply {
                            this.name ="Bench Press"
                        }

                    this.setList = realmListOf(Set(),Set())
                },
            )
        },
        onClick = {}
    )
}