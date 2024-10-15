package com.ronit.liftlog.routine_feature.presntation.routine_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ronit.liftlog.core.presentation.component.BottomBar
import com.ronit.liftlog.core.presentation.component.SwipeToDeleteContainer
import com.ronit.liftlog.routine_feature.presntation.routine_list.components.RoutineCard
import com.ronit.liftlog.routine_feature.presntation.routine_list.event.RoutineListScreenEvent
import com.ronit.liftlog.ui.theme.black
import com.ronit.liftlog.ui.theme.primary
import com.ronit.liftlog.ui.theme.primaryText


@Composable
fun RoutineListScreen(
    modifier: Modifier = Modifier,
    navController:NavController,
    onAddRoutine:()->Unit,
    onStartRoutineClicked:(routineId:String,routineName:String)->Unit,
    onCardClicked:(id:String)->Unit
) {

    val viewModel  = hiltViewModel<RoutineListScreenViewModel>()

    val snackbarHostState = remember { SnackbarHostState()}

    Scaffold (

        bottomBar = { BottomBar(navController = navController) }
    ){paddingValues->


        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()

                .background(color = black),
            verticalArrangement = Arrangement.SpaceBetween
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier,
                    text = "Routines",
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold),
                    color = primaryText
                )

                OutlinedButton(

                    modifier = Modifier
                        .height(40.dp)
                        .wrapContentWidth(),
                    border = BorderStroke(width = 1.dp, color = primary),


                    colors = ButtonDefaults.buttonColors(containerColor = black),
                    onClick = onAddRoutine,

                    ) {
                    Text(
                        text = "Create +",
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = primaryText
                    )
                }
            }


                LazyColumn(
                    modifier = Modifier
                        .padding(start = 16.dp , end = 16.dp)
                        .fillMaxSize()
                ) {


                    items(items = viewModel.routineList) {

                        SwipeToDeleteContainer(

                            onDelete = { viewModel.onEvent(RoutineListScreenEvent.OnDeleteRoutine(it))},
                            dialogTitleText = "This Action Cannot be undone! ",
                            dialogTextText = "Are you sure you want delete this workout? ",
                            content = {
                                RoutineCard(

                                    routineName = it.name,
                                    exerciseCount = it.exerciseIds.size,
                                    onCardClick = { onCardClicked(it._id.toHexString()) },
                                    onStartNowClick = {
                                        onStartRoutineClicked(
                                            it._id.toHexString(),
                                            it.name
                                        )
                                    },
                                )
                            }
                        )


                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }

        }
    }




}


@Preview
@Composable
fun RoutinesScreenPreview() {
    RoutineListScreen(onStartRoutineClicked = {id,name ->}, onCardClicked = {} ,onAddRoutine = {}, navController = rememberNavController())
}