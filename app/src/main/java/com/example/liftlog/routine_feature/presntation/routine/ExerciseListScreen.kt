package com.example.liftlog.routine_feature.presntation.routine

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.routine_feature.presntation.routine_list.components.RoutineCard
import com.example.liftlog.core.presentation.component.SearchBar
import com.example.liftlog.core.presentation.component.ThreeSectionTopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseListScreen(
    modifier: Modifier = Modifier,
    routineId:String?,
    onCancelBtnClicked:()->Unit,
    onDoneAddingExercises:(exercises:List<Exercise>)->Unit,
    onCreateNewExercise:()->Unit
) {


    val viewModel: ExerciseListScreenViewModel = hiltViewModel<ExerciseListScreenViewModel,ExerciseListScreenViewModel.ExerciseLostScreenViewModelFactory>{factory->
        factory.create(routineId)

    }

    val exerciseSelectedColor = MaterialTheme.colorScheme.primaryContainer
    val exerciseNotSelectedColor= MaterialTheme.colorScheme.background
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            // Top Bar
            ThreeSectionTopBar(
                modifier = Modifier.height(55.dp),
                leftContent = {
                    TextButton(
                        modifier = Modifier,
                        onClick = onCancelBtnClicked
                    ) {

                        Text(
                            modifier = Modifier,
                            text = "Cancel",
                            fontSize = 15.sp
                        )

                    }
                },

                middleContent = {
                    Text(
                        text = "Select Exercise",
                        fontSize = 16.sp
                    )
                },

                rightContent = {
                    IconButton(
                        onClick = onCreateNewExercise
                    ) {
                        Image(imageVector = Icons.Default.Add, contentDescription = "Add Exercise")
                    }

                    IconButton(
                        onClick = {
                            onDoneAddingExercises(
                                viewModel.selectedExercises
                            )
                        }
                    ) {
                        Image(imageVector = Icons.Default.Done, contentDescription = "Add Exercise")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(

            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, bottom = 4.dp),

            verticalArrangement = Arrangement.Top
        ) {


            Spacer(modifier = Modifier.height(10.dp))

            SearchBar()


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp, bottom = 4.dp, top = 20.dp),
            ){

                items(items = viewModel.exerciseListFlow.value, key = {it._id.toHexString()}){ exercise->



                    val dismissState =  rememberSwipeToDismissBoxState(
                        confirmValueChange = {state->

                            if(state == SwipeToDismissBoxValue.EndToStart){

                                true
                            }
                            else{
                                false
                            }

                        }
                    )
                    val selected = remember(viewModel.selectedExercises.toList()) {

                            viewModel.selectedExercises.any {
                                it._id.toHexString() == exercise._id.toHexString()
                            }

                    }
                    val bg = if(selected){
                            exerciseSelectedColor
                        }
                        else{
                            exerciseNotSelectedColor
                        }



                        RoutineCard(
                            routineName = exercise.name,
                            onCardClick = {viewModel.onExerciseSelected(exercise)},
                            onStartNowClick = {},
                            exerciseCount = 1,
                        )

                }
            }
        }
    }


}




@Preview(showBackground = true)
@Composable
private fun ExerciseListScreenPreview() {

    ExerciseListScreen(routineId = null,onCancelBtnClicked = {}, onDoneAddingExercises = {}, onCreateNewExercise = {})

}

@Preview(showBackground = true)
@Composable
fun ExerciseScreenTopBarPreview() {

    ThreeSectionTopBar(
        modifier = Modifier.height(55.dp),
        leftContent = {
            TextButton(onClick = { TODO("Implement Cancel Button") }) {

                Text(
                    modifier = Modifier,
                    text = "Cancel",
                    fontSize = 15.sp
                )

            }
        },

        middleContent = {
            Text(
                text = "Select Exercise",
                fontSize = 16.sp
            )
        },

        rightContent = {
            IconButton(onClick = { TODO("Implement Add Exercise Button") }) {
                Image(imageVector = Icons.Default.Add, contentDescription = "Add Exercise")
            }
        }
    )
    
}