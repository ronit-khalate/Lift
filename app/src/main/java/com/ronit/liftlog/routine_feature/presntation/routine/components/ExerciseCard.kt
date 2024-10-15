package com.ronit.liftlog.routine_feature.presntation.routine.components



import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ronit.liftlog.core.data.model.entity.Exercise
import com.ronit.liftlog.core.di.RetrofitModule
import com.ronit.liftlog.routine_feature.presntation.components.ImageSlider
import com.ronit.liftlog.ui.theme.body
import com.ronit.liftlog.ui.theme.neutral
import com.ronit.liftlog.ui.theme.primary
import com.ronit.liftlog.ui.theme.primaryText


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseCard(
    modifier: Modifier = Modifier,
    selected:Boolean = false,
    onClick:()->Unit,
    exercise:Exercise,
    muscleGroup:List<String>,
    expandable:Boolean = false,
    shape: Shape =  MaterialTheme.shapes.large
) {


    var showImage by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .border(width = 2.dp , color = if(!selected) Color.Transparent else primary, shape = shape)
            .fillMaxWidth()
            .animateContentSize()
            .combinedClickable (
                onClick = {onClick()},
                onLongClick = {
                    showImage = !showImage
                }
            )
           ,
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = neutral),
    ) {



        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {






            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier
                    .fillMaxSize(),
                visible = showImage,
                enter = slideInVertically(),
                exit = slideOutVertically()
            ) {


                ImageSlider(uriList = exercise.images)



            }




            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                ) {
                    Text(
                        text = exercise.name,
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
}

@Preview
@Composable
private fun ExerciseCardPreview() {

    ExerciseCard(muscleGroup = listOf("chest"), exercise= Exercise(), onClick = {})
}