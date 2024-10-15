package com.ronit.liftlog.core.presentation.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ronit.liftlog.core.data.MuscleGroup
import com.ronit.liftlog.core.domain.titlecase
import com.ronit.liftlog.core.presentation.component.BasicDialog
import com.ronit.liftlog.core.presentation.component.ThreeSectionTopBar
import com.ronit.liftlog.core.presentation.exercise.event.ExerciseScreenEvent
import com.ronit.liftlog.core.presentation.exercise.state.ExerciseScreenState
import com.ronit.liftlog.ui.theme.black
import com.ronit.liftlog.ui.theme.body
import com.ronit.liftlog.ui.theme.primary
import com.ronit.liftlog.ui.theme.primaryText



@Composable
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    uiState:ExerciseScreenState,
    navController: NavController,
    muscleGroupIdx: Int,
    onEvent:(ExerciseScreenEvent)->Unit
) {





    if(uiState.showDialog && uiState.dialogContent != null){

        BasicDialog(
            onDismiss = {uiState.dialogContent.onDismiss?.invoke()},
            onConfirm = { uiState.dialogContent.onConfirm?.invoke()},
            dismissBtnText = uiState.dialogContent.dismissBtnText ,
            confirmBtnText = uiState.dialogContent.confirmBtnText,
            titleText = uiState.dialogContent.title ,
            textString = uiState.dialogContent.text
        )
    }








    Scaffold(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars)
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


                        onEvent( ExerciseScreenEvent.OnDoneBtnClicked{ navController.popBackStack() })

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
            ){

                ExerciseNameTextField(
                    modifier = Modifier,
                    name = uiState.name,
                    onNameEntered = {onEvent(ExerciseScreenEvent.OnNameChange(it))}
                )
            }





            MuscleGroupChips(muscleGroupIdx) {
                onEvent(ExerciseScreenEvent.OnMuscleGroupChange(it))
            }




            



        }

    }

}


@Composable
private fun ExerciseNameTextField(
    modifier: Modifier = Modifier,
    name:String,
    onNameEntered:(String)->Unit
) {

//    val focusRequester = remember { FocusRequester() }
//
//    LaunchedEffect(Unit) {
//
//        focusRequester.requestFocus()
//    }

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        textStyle = MaterialTheme.typography.headlineSmall.copy(color = primaryText),
        cursorBrush = SolidColor(primaryText),
        singleLine = false,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        value = name,
        onValueChange = { onNameEntered(it) },
        decorationBox = {

            if (name.isBlank()) {

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


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MuscleGroupChips(
    selectedMuscleGroups: Int,
    onMuscleGroupSelected: (Int) -> Unit
) {
    ContextualFlowRow(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp),

        itemCount = MuscleGroup.entries.size
    ) {
            FilterChip(
                modifier = Modifier
                    .padding(bottom = 6.dp, end = 6.dp)
                    .height(32.dp),
                selected = it==selectedMuscleGroups,
                onClick = { onMuscleGroupSelected(it) },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = black,
                    selectedContainerColor = primary,
                    labelColor = primaryText,
                    selectedLabelColor = black
                ),
                label = {
                    Text(
                        text = MuscleGroup.entries[it].name,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )

    }
}


@Preview
@Composable
fun ExerciseScreenPreview() {

    ExerciseScreen(uiState = ExerciseScreenState(), navController = rememberNavController(), onEvent = { it->}, muscleGroupIdx = 1)

}