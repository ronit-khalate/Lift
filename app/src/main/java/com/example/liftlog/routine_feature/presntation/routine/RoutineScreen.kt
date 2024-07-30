package com.example.liftlog.routine_feature.presntation.routine

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.liftlog.core.presentation.component.ClickableRow
import com.example.liftlog.core.presentation.component.ThreeSectionTopBar


/*
*
* Sate
*   Name
*   note
*
* Event
*   onAddExercie
*   OnNameChange
*   onStartRoutine
*   onAddExercise
*   onBackPress
*
*
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineScreen(
    modifier: Modifier = Modifier,
    routineId:String?=null
) {

    val viewModel = hiltViewModel<RoutineScreenViewModel,RoutineScreenViewModel.RoutineScreenViewModelFactory> {factory ->
        factory.create(routineId)

    }


    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),

        topBar = {
            ThreeSectionTopBar(
                modifier = Modifier
                    .height(70.dp)
                    .padding(15.dp),
                leftContent = {
                    Row(
                        modifier =Modifier
                        ,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            modifier = Modifier
                                .size(35.dp),
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)

                            )

                        Spacer(modifier = Modifier.width(4.dp))


                        Text(
                            fontSize = 20.sp,
                            text = "Routines"
                        )
                    }
                },

                rightContent = {

                        Image(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Routine",
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
                        )

//                        Spacer(modifier = Modifier.width(1.dp))

                }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
                .verticalScroll(state = rememberScrollState())
        ) {


            // Routine Name
            Row (
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    text = if(viewModel.state.routineName.isBlank()) "Unnamed Routine" else viewModel.state.routineName
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            // start this workout button
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                onClick = { /*TODO*/ }
            ) {
                Row (
                    Modifier
                        .padding(start = 10.dp)
                        .fillMaxSize()
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        fontSize = 15.sp,
                        text = "Start this workout"
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // info card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                onClick = { /*TODO*/ }

            ) {

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center
                ) {


                    Row(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp),
                            value = viewModel.state.routineName,
                            onValueChange = {  },
                            textStyle = TextStyle(
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.CenterStart
                                ) {

                                    if (viewModel.state.routineName.isBlank()) {
                                        Text(
                                            text = "Unnamed Routine",
                                            style = TextStyle(
                                                fontSize = 15.sp,
                                                color = MaterialTheme.colorScheme.secondary
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
                            .padding(start = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp),
                            value = viewModel.state.routineName,
                            onValueChange = {  },
                            textStyle = TextStyle(
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.CenterStart
                                ) {


                                    if (viewModel.state.note.isBlank()) {
                                        Text(
                                            text = "Notes",
                                            style = TextStyle(
                                                fontSize = 15.sp,
                                                color = MaterialTheme.colorScheme.secondary
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

            Spacer(modifier = Modifier.height(30.dp))

            //? Exercises

            if(viewModel.state.exerciseList.isNotEmpty()) {


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {

                        viewModel.state.exerciseList.forEach {

                            ClickableRow(height = 50, text = it.name, onClick = { /*TODO*/ }) {
                                Image(
                                    modifier = Modifier
                                        .size(20.dp),
                                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)

                                )
                            }

                            HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
                        }
                    }

                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                onClick = { /*TODO*/ }
            ) {
                Row (
                    Modifier
                        .padding(start = 10.dp)
                        .fillMaxSize()
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        fontSize = 15.sp,
                        text = "Add Exercise"
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))



        }


    }
}


@Preview(
    showBackground = true
)
@Composable
fun RoutineScreenPreview() {
    RoutineScreen(routineId = "")
}