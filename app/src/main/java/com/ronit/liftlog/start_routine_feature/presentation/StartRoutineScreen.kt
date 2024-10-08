package com.ronit.liftlog.start_routine_feature.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ronit.liftlog.core.presentation.component.BottomBar
import com.ronit.liftlog.start_routine_feature.presentation.components.ExerciseSetLogCard
import com.ronit.liftlog.start_routine_feature.presentation.components.StartRoutineScreenTopBar
import com.ronit.liftlog.start_routine_feature.presentation.event.StartRoutineScreenEvent
import com.ronit.liftlog.ui.theme.black
import com.ronit.liftlog.ui.theme.primary
import com.ronit.liftlog.ui.theme.primaryText

@Composable
fun StartRoutineScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    routineId:String,
    routineName:String

) {


    val context = LocalContext.current

    val viewmodel:StartRoutineViewModel = hiltViewModel<StartRoutineViewModel,StartRoutineViewModel.StartRoutineViewModelFactory>() {

        it.create(routineId,routineName)

    }



    Scaffold(
        modifier=modifier
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar =  {
            StartRoutineScreenTopBar(
                onBackNavigate = { navController.navigateUp() },

            )
        },

    ) {paddingValues->


        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .background(color = black),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {



            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = viewmodel.routineName,
                    color = primaryText,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = {
                        viewmodel.onEvent(StartRoutineScreenEvent.OnRoutineFinish)
                    navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = primary)
                ) {
                    Text(
                        text = "Stop 00:00:00",
                        style = MaterialTheme.typography.labelSmall.copy(color = black, fontWeight = FontWeight.Bold)
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {




                items(items = viewmodel.state.exercisesLog, key = {it.id.toHexString()}) {exerciseLog->

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
    StartRoutineScreen(routineId = "r",  navController = rememberNavController() , routineName = "")
}