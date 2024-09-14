package com.example.liftlog.core.presentation.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowColumn
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.liftlog.core.presentation.component.ThreeSectionTopBar
import com.example.liftlog.core.presentation.exercise.event.ExerciseScreenEvent
import com.example.liftlog.routine_feature.presntation.routine.event.RoutineScreenEvent
import com.example.liftlog.ui.theme.black
import com.example.liftlog.ui.theme.body
import com.example.liftlog.ui.theme.primary


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    exerciseId:String?=null,
    navController: NavController
) {


    val viewModel = hiltViewModel <ExerciseViewModel,ExerciseViewModel.ExerciseViewModelFactory>{ factory->

        factory.create(exerciseId)

    }


    Scaffold(
        modifier = modifier

            .fillMaxSize(),

        topBar = {
            ThreeSectionTopBar(
                leftContent = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft, contentDescription = "")
                    }
                },

                middleContent = {

                    Text(
                        text = "Add Exercise",
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodySmall
                    )
                },

                rightContent = {
                    IconButton(onClick = {
                        viewModel.onEvent(
                            ExerciseScreenEvent.OnDoneBtnClicked{
                                navController.popBackStack()
                            }
                        )
                    }) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "")
                    }
                }
            )
        }
    ) {


        

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {




            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textStyle = MaterialTheme.typography.headlineSmall.copy(color = primary),

                    value = viewModel.exercise.name,
                    onValueChange = { viewModel.onEvent(ExerciseScreenEvent.OnNameChange(it)) },
                    decorationBox = {

                        if (viewModel.exercise.name.isBlank()) {

                            Text(
                                text = "Exercise Name",
                                style = MaterialTheme.typography.headlineSmall,
                                color = body
                            )
                        }
                        it()
                    }
                )
            }





            ContextualFlowRow(
                modifier = Modifier
                    .padding(start = 16.dp , end = 16.dp ),
                itemCount = 10,

            ) {
                this.lineIndex
                FilterChip(
                    modifier = Modifier
                        .padding(bottom = 6.dp, end = 6.dp)
                        .height(32.dp)

                        ,
                    selected = true,
                    onClick = { /*TODO*/ },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = black,
                        selectedContainerColor = primary,
                        labelColor = primary,
                        selectedLabelColor = black
                    ),
                    label = {
                        Text(
                            text = "Chest",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )


                


            }



            



        }

    }

}

@Preview
@Composable
fun ExerciseScreenPreview() {

    ExerciseScreen(navController = rememberNavController())

}