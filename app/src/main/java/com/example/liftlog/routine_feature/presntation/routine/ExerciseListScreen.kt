package com.example.liftlog.routine_feature.presntation.routine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.liftlog.core.data.model.Exercise
import com.example.liftlog.core.presentation.component.MaxWidthButton
import com.example.liftlog.routine_feature.presntation.routine_list.components.RoutineCard
import com.example.liftlog.core.presentation.component.SearchBar
import com.example.liftlog.core.presentation.component.ThreeSectionTopBar
import com.example.liftlog.routine_feature.presntation.routine.components.ExerciseCard
import com.example.liftlog.routine_feature.presntation.routine.components.SelectedIdentifier
import com.example.liftlog.routine_feature.presntation.routine.event.ExerciseListUiEvent
import com.example.liftlog.ui.theme.black
import com.example.liftlog.ui.theme.blue
import com.example.liftlog.ui.theme.primary
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged


@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
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

    val chipListScrollState =  rememberScrollState()

    LaunchedEffect(key1 = viewModel.searchQuery) {
        snapshotFlow { viewModel.searchQuery }
            .distinctUntilChanged()
            .collectLatest {
                viewModel.onSearchQueryEntered(query = it)
            }
    }

    Scaffold(
        modifier = modifier

            .fillMaxSize(),
        topBar = {
            // Top Bar
            ThreeSectionTopBar(
                modifier = Modifier.height(55.dp),
                leftContent = {
                   IconButton(onClick =onCancelBtnClicked) {
                       Icon(
                           imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                           contentDescription = "cancel",
                           tint = primary
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
                        onClick = {
                            onDoneAddingExercises(
                                viewModel.selectedExercises
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            tint = primary,
                            contentDescription = "Add Exercise"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(

            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize(),

            verticalArrangement = Arrangement.Top
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {

                SearchBar(
                    query = viewModel.searchQuery,
                    onQueryEntered = {
                        viewModel.onUiEvent(ExerciseListUiEvent.OnSearchQueryEntered(it))
                    },
                    onSearch = {},
                    label = "Search Exercise"
                )
            }

           LazyRow(
               modifier = Modifier
                   .fillMaxWidth()

           ) {


               item {
                   Spacer(Modifier.width(16.dp))
               }


               items(10){

                   FilterChip(
                       modifier = Modifier,
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

                   Spacer(modifier = Modifier.width(16.dp))
               }
           }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
            ){

                items(items = viewModel.exerciseListFlow.value, key = {it._id.toHexString()}){ exercise->



                    val selected = remember(viewModel.selectedExercises.toList()) {

                            viewModel.selectedExercises.any {
                                it._id.toHexString() == exercise._id.toHexString()
                            }

                    }



                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp , end = 16.dp , top=8.dp , bottom = 8.dp)
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        AnimatedVisibility(
                            visible = selected,
                        ) {
                            SelectedIdentifier(color = blue)


                        }

                        if(selected){
                            Spacer(modifier = Modifier.width(8.dp))
                        }


                        ExerciseCard(
                            onClick = { viewModel.onExerciseSelected(exercise) },
                            exerciseName = exercise.name,
                            muscleGroup = exercise.muscleGroup ?: ""
                        )
                    }
                    



                }
            }

            MaxWidthButton(
                modifier = Modifier.padding(16.dp),
                onClick = onCreateNewExercise,
                text = "Create Exercise"
            )
        }
    }


}




@Preview(showBackground = true)
@Composable
private fun ExerciseListScreenPreview() {

    ExerciseListScreen(routineId = null, onCancelBtnClicked = {}, onDoneAddingExercises = {}, onCreateNewExercise = {})

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