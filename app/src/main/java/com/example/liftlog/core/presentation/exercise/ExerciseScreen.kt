package com.example.liftlog.core.presentation.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
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
                    TextButton(onClick = {navController.popBackStack()}) {
                        Text("Cancel")
                        
                    }
                },

                middleContent = {

                    Text(text = "Add Exercise")
                },

                rightContent = {
                    TextButton(
                        onClick = {
                            viewModel.onEvent(
                                ExerciseScreenEvent.onDoneBtnClicked{
                                    navController.popBackStack()
                                }
                            )
                        }
                    ) {
                        Text(text = "Done")
                    }
                }
            )
        }
    ) {


        

        Column(
            modifier = Modifier
                .padding(it)
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(60.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()

                        .wrapContentHeight(),
                    onClick = { /*TODO*/ }

                ) {



                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BasicTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp),
                                value = viewModel.exercise.name,
                                onValueChange = { viewModel.onEvent(ExerciseScreenEvent.OnNameChange(it)) },
                                textStyle = TextStyle(
                                    fontSize = 15.sp
                                ),
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.CenterStart
                                    ) {

                                        // TODO correct if
                                        if (viewModel.exercise.name.isEmpty()) {
                                            Text(
                                                text = "Name",
                                                style = TextStyle(
                                                    fontSize = 15.sp,
                                                    color = Color.Gray
                                                )
                                            )
                                        }
                                        innerTextField()
                                    }
                                }
                            )
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BasicTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp),
                                value = viewModel.exercise.muscleGroup?:"",
                                onValueChange = { viewModel.onEvent(ExerciseScreenEvent.OnMuscleGroupChange(it)) },
                                textStyle = TextStyle(
                                    fontSize = 15.sp
                                ),
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.CenterStart
                                    ) {

                                        // TODO correct if
                                        if (viewModel.exercise.muscleGroup.isNullOrEmpty()) {
                                            Text(
                                                text = "Muscle Group",
                                                style = TextStyle(
                                                    fontSize = 15.sp,
                                                    color = Color.Gray
                                                )
                                            )
                                        }
                                        innerTextField()
                                    }
                                }
                            )
                        }

                }
            
                Spacer(modifier = Modifier.height(35.dp))

                Text(
                    modifier = Modifier
                        .padding(start = 10.dp, bottom = 5.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    text = "Note"
                )
            
                Card(
                    modifier = Modifier
                        .fillMaxWidth()

                        .wrapContentHeight(),
                    onClick = { /*TODO*/ }

                ) {




                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BasicTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 30.dp, max = Dp.Infinity),
                                value = viewModel.exercise.note?:"",
                                onValueChange = { viewModel.onEvent(ExerciseScreenEvent.OnNoteChange(it)) },
                                singleLine = false,

                                textStyle = TextStyle(
                                    fontSize = 15.sp,
                                ),
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.CenterStart
                                    ) {

                                        // TODO correct if
                                        if (viewModel.exercise.note.isNullOrEmpty()) {
                                            Text(
                                                text = "Description",
                                                style = TextStyle(
                                                    fontSize = 15.sp,
                                                    color = Color.Gray
                                                )
                                            )
                                        }
                                        innerTextField()
                                    }
                                }
                            )
                        }



                }


        }

    }

}

@Preview
@Composable
fun ExerciseScreenPreview() {

    ExerciseScreen(navController = rememberNavController())

}