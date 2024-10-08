package com.ronit.liftlog.routine_feature.presntation.routine.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ronit.liftlog.R
import com.ronit.liftlog.ui.theme.body
import com.ronit.liftlog.ui.theme.neutral
import com.ronit.liftlog.ui.theme.primaryText


@Composable
fun ExerciseCard(
    modifier: Modifier = Modifier,
    onClick:()->Unit,
    exerciseName:String,
    muscleGroup:List<String>
) {

    Card(
        modifier = modifier
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
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column (
                modifier = Modifier
                    .wrapContentHeight()
            ){
                Text(
                    text = exerciseName,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = primaryText
                )

                Row {

                    muscleGroup.forEach {
                        Text(
                            text = it,
                            fontSize = MaterialTheme.typography.labelSmall.fontSize,
                            color = body
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
private fun ExerciseCardPreview() {

    ExerciseCard(muscleGroup = listOf("chest"), exerciseName = "Bench Press", onClick = {})
}