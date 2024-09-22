package com.ronit.liftlog.core.presentation.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ronit.liftlog.core.data.MuscleGroup
import com.ronit.liftlog.core.domain.TitleCaseStringFormatter
import com.ronit.liftlog.core.presentation.component.BasicDialog
import com.ronit.liftlog.core.presentation.component.ThreeSectionTopBar
import com.ronit.liftlog.core.presentation.exercise.event.ExerciseScreenEvent
import com.ronit.liftlog.ui.theme.black
import com.ronit.liftlog.ui.theme.body
import com.ronit.liftlog.ui.theme.primary


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


    if(viewModel.exercise.showDialog && viewModel.exercise.dialogContent != null){

        BasicDialog(
            onDismiss = {viewModel.exercise.dialogContent!!.onDismiss?.invoke()},
            onConfirm = { viewModel.exercise.dialogContent!!.onConfirm?.invoke()},
            dismissBtnText = viewModel.exercise.dialogContent!!.dismissBtnText ,
            confirmBtnText = viewModel.exercise.dialogContent!!.confirmBtnText,
            titleText = viewModel.exercise.dialogContent!!.title ,
            textString = viewModel.exercise.dialogContent!!.text
        )
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
    ) { it ->


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

                    value = viewModel.exercise.name.replaceFirstChar { char -> char.titlecase() },
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
                itemCount = MuscleGroup.entries.size,

            ) {


                val group = MuscleGroup.entries[it]


                    FilterChip(
                        modifier = Modifier
                            .padding(bottom = 6.dp, end = 6.dp)
                            .height(32.dp),
                        selected = viewModel.exercise.muscleGroupIdx== group.ordinal,
                        onClick = { viewModel.onEvent(ExerciseScreenEvent.OnMuscleGroupChange(group.ordinal)) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = black,
                            selectedContainerColor = primary,
                            labelColor = primary,
                            selectedLabelColor = black
                        ),
                        label = {
                            Text(
                                text = group.toString().format(TitleCaseStringFormatter()),
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