package com.ronit.liftlog.routine_feature.presntation.routine.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.gestures.snapping.snapFlingBehavior
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.Coil
import coil.compose.AsyncImage
import com.ronit.liftlog.R
import com.ronit.liftlog.core.data.model.entity.Exercise
import com.ronit.liftlog.core.di.RetrofitModule
import com.ronit.liftlog.ui.theme.body
import com.ronit.liftlog.ui.theme.neutral
import com.ronit.liftlog.ui.theme.primaryText


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseCard(
    modifier: Modifier = Modifier,
    onClick:()->Unit,
    exercise:Exercise,
    muscleGroup:List<String>,
    expandable:Boolean = false,
    shape: Shape =  MaterialTheme.shapes.large
) {


    var showImage by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(300))
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


        AnimatedVisibility(
            modifier = Modifier
                .fillMaxSize(),
            visible = showImage,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {



                AsyncImage(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    alignment = Alignment.Center,

                    contentScale = ContentScale.Crop,
                    model = "${RetrofitModule.provideBaseUrl()}exercises/${
                        exercise.images.first().trim()
                    }",
                    contentDescription = null,

                )

                AsyncImage(
                    modifier = Modifier
                        .fillMaxHeight(),
                    alignment = Alignment.Center,

                    contentScale = ContentScale.Crop,
                    model = "${RetrofitModule.provideBaseUrl()}exercises/${
                        exercise.images[1].trim()
                    }",
                    contentDescription = null,

                    )

        }




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

@Preview
@Composable
private fun ExerciseCardPreview() {

    ExerciseCard(muscleGroup = listOf("chest"), exercise= Exercise(), onClick = {})
}