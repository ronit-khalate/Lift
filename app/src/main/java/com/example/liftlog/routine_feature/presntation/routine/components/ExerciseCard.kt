package com.example.liftlog.routine_feature.presntation.routine.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.liftlog.ui.theme.neutral
import com.example.liftlog.ui.theme.primary


@Composable
fun ExerciseCard(
    modifier: Modifier = Modifier,
    exerciseName:String,
    muscleGroup:String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .height(68.dp),
        colors = CardDefaults.cardColors(containerColor = neutral),
        shape = MaterialTheme.shapes.large
    ) {


        Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            Column {
                Text(
                    text = exerciseName,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = primary
                )
                Text(
                    text = muscleGroup,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    color = primary
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExerciseCardPreview() {

    ExerciseCard(muscleGroup = "Chest", exerciseName = "Bench Press")
}