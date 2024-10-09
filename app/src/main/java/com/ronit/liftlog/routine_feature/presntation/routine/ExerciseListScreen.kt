package com.ronit.liftlog.routine_feature.presntation.routine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.animateScrollBy
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
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ronit.liftlog.core.data.MuscleGroup
import com.ronit.liftlog.core.data.model.entity.Exercise
import com.ronit.liftlog.core.presentation.component.MaxWidthButton
import com.ronit.liftlog.core.presentation.component.SearchBar
import com.ronit.liftlog.core.presentation.component.ThreeSectionTopBar
import com.ronit.liftlog.routine_feature.presntation.routine.components.ExerciseCard
import com.ronit.liftlog.routine_feature.presntation.routine.components.SelectedIdentifier
import com.ronit.liftlog.routine_feature.presntation.routine.event.ExerciseListUiEvent
import com.ronit.liftlog.ui.theme.black
import com.ronit.liftlog.ui.theme.primary
import com.ronit.liftlog.ui.theme.primaryText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


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

    val coroutineScope = rememberCoroutineScope()


    val exerciseListState = rememberLazyListState()

    LaunchedEffect(key1 = viewModel.searchQuery) {
        snapshotFlow { viewModel.searchQuery }
            .distinctUntilChanged()
            .debounce(300L)
            .collectLatest {

                exerciseListState.animateScrollToItem(0)

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
               ,


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
                           coroutineScope.launch {
                               exerciseListState.animateScrollToItem(0)
                           }
                       },
                       colors = FilterChipDefaults.filterChipColors(
                           containerColor = black,
                           selectedContainerColor = primary,
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
                state = exerciseListState
            ){

                items(items = viewModel.shownExercise, key = {it._id.toHexString()}){ exercise->

                    Row(
                        modifier = Modifier

                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                            .clip(MaterialTheme.shapes.large)
                            .height(IntrinsicSize.Max)
                            .animateItem()
                            ,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        val selected by remember{

                            derivedStateOf{
                                viewModel.selectedExercises.any {
                                    it._id.toHexString() == exercise._id.toHexString()
                                }
                            }


                        }
                        AnimatedVisibility(
                            visible = selected,
                            enter = slideInHorizontally(
                                animationSpec = tween(
                                    easing = FastOutLinearInEasing
                                )
                            ),
                            exit = slideOutHorizontally(
                                animationSpec = tween(
                                    easing = EaseOut
                                )
                            ),
                        ) {
                            SelectedIdentifier(
                                color = primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                        }


                        ExerciseCard(
                            onClick = { viewModel.onExerciseSelected(exercise) },
                            exercise = exercise,
                            muscleGroup = exercise.primaryMuscles,
                            shape = if(selected) {MaterialTheme.shapes.large.copy(topStart = CornerSize(0.dp), bottomStart = CornerSize(0.dp))}else{MaterialTheme.shapes.large}
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