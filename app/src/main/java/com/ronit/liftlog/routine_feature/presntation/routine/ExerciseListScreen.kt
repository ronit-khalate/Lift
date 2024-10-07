package com.ronit.liftlog.routine_feature.presntation.routine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ronit.liftlog.core.data.MuscleGroup
import com.ronit.liftlog.core.data.model.Exercise
import com.ronit.liftlog.core.presentation.component.MaxWidthButton
import com.ronit.liftlog.core.presentation.component.SearchBar
import com.ronit.liftlog.core.presentation.component.ThreeSectionTopBar
import com.ronit.liftlog.routine_feature.presntation.routine.components.ExerciseCard
import com.ronit.liftlog.routine_feature.presntation.routine.components.SelectedIdentifier
import com.ronit.liftlog.routine_feature.presntation.routine.event.ExerciseListUiEvent
import com.ronit.liftlog.ui.theme.black
import com.ronit.liftlog.ui.theme.blue
import com.ronit.liftlog.ui.theme.primary
import com.ronit.liftlog.ui.theme.primaryText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
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


    val list = viewModel.exerciseListFlow.collectAsState()

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
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            // Top Bar
            ThreeSectionTopBar(
                modifier = Modifier.height(55.dp),
                leftContent = {
                   IconButton(onClick =onCancelBtnClicked) {
                       Icon(
                           imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                           contentDescription = "cancel",
                           tint = primaryText
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
                            tint = primaryText,
                            contentDescription = "Add Exercise"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(

            modifier = Modifier
                .padding(paddingValues)
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


               items(items = MuscleGroup.entries){

                   FilterChip(
                       modifier = Modifier,
                       selected = viewModel.selectedMuscleGroup == it,
                       onClick = {
                           if(viewModel.selectedMuscleGroup == it){
                               viewModel.onUiEvent(ExerciseListUiEvent.MuscleGroupSelected(null))
                           }
                           else{
                               viewModel.onUiEvent(ExerciseListUiEvent.MuscleGroupSelected(it))
                           }
                       },
                       colors = FilterChipDefaults.filterChipColors(
                           containerColor = black,
                           selectedContainerColor = primaryText,
                           labelColor = primaryText,
                           selectedLabelColor = black
                       ),
                       label = {
                           Text(
                               text = it.name.replaceFirstChar { char -> char.titlecase() },
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
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        AnimatedVisibility(
                            visible = selected,
                        ) {
                            SelectedIdentifier(color = primary)

                        }

                        if(selected){
                            Spacer(modifier = Modifier.width(8.dp))
                        }


                        ExerciseCard(
                            onClick = { viewModel.onExerciseSelected(exercise) },
                            exerciseName = exercise.name.replaceFirstChar { char -> char.titlecase() },
                            muscleGroup = exercise.muscleGroup ?: "",
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