package com.example.liftlog.start_routine_feature.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.liftlog.start_routine_feature.presentation.components.ExerciseSetLogCard
import com.example.liftlog.start_routine_feature.presentation.event.StartRoutineScreenEvent
import com.example.liftlog.ui.theme.black
import com.example.liftlog.ui.theme.white

@Composable
fun StartRoutineScreen(
    modifier: Modifier = Modifier,
    topbar: @Composable ()->Unit,
    routineId:String,

) {

    val viewmodel:StartRoutineViewModel = hiltViewModel<StartRoutineViewModel,StartRoutineViewModel.StartRoutineViewModelFactory>() {

        it.create(routineId,"Back")

    }

    Scaffold(
        topBar = topbar
    ) {paddingValues->


        Column(

            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .fillMaxSize()
                .background(color = black),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {





            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Text(
                            text = "routineName",
                            color = white,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(48.dp))
                }


                items(items = viewmodel.state.exercisesLog, key = {it._id.toHexString()}) {exerciseLog->

                    var count= 1
                    ExerciseSetLogCard(
                        exerciseLog = exerciseLog,
                        count =count,
                        onAddSetBtnClick = {viewmodel.onEvent(StartRoutineScreenEvent.OnAddSetInExerciseLog(it))},
                        updateWeight = {id:String , exLogId,data:String ->

                            viewmodel.onEvent(StartRoutineScreenEvent.OnUpdateWeight(id = id, data = data,exLogId =exLogId))
                        },
                        updateReps = { id:String , exLogId,data:String ->

                            viewmodel.onEvent(StartRoutineScreenEvent.OnUpdateReps(id=id,data=data,exLogId=exLogId))

                        },
                        updateNotes = {id:String , exLogId,data:String ->
                            viewmodel.onEvent(StartRoutineScreenEvent.OnUpdateNotes(id=id,data=data,exLogId=exLogId))
                        }
                    )

                    count++
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }

        }
    }
}


@Preview(
    showBackground = true
)
@Composable
private fun StartRoutineScreenPreview() {
    StartRoutineScreen(routineId = "r", topbar = {} )
}