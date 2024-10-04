package com.ronit.liftlog.routine_feature.presntation.routine.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ronit.liftlog.ui.theme.body
import com.ronit.liftlog.ui.theme.neutral
import com.ronit.liftlog.ui.theme.primaryText


@Composable
fun ExerciseCard(
    modifier: Modifier = Modifier,
    onClick:()->Unit,
    exerciseName:String,
    muscleGroup:String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = neutral),
        shape = MaterialTheme.shapes.large
    ) {


        Row (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ){
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ){
                Text(
                    text = exerciseName,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = primaryText
                )
                Text(
                    text = muscleGroup,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    color = body
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExerciseCardPreview() {

    ExerciseCard(muscleGroup = "Chest", exerciseName = "Bench Press", onClick = {})
}