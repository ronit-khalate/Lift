package com.example.liftlog.routine_feature.presntation.routine_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.liftlog.routine_feature.presntation.routine_list.components.RoutineCard
import com.example.liftlog.ui.theme.black
import com.example.liftlog.ui.theme.primary


@Composable
fun RoutineListScreen(
    modifier: Modifier = Modifier,
    onAddRoutine:()->Unit,
    onRoutineClicked:(id:String,name:String)->Unit
) {

    val viewModel  = hiltViewModel<RoutineListScreenViewModel>()


    val brush = Brush.linearGradient(
        0.6f to Color(0xFFFF9900),
        3.0f to Color(0xFFAD00FF),
        start = Offset(0.0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 10.0f)

    )

    var listCardHeight by remember {
        mutableIntStateOf(684)
    }

    var barPadding by remember {
        mutableIntStateOf(20)
    }


        Column(
            modifier = Modifier
                .fillMaxSize()

                .background(brush),
            verticalArrangement = Arrangement.SpaceBetween
        ) {



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = barPadding.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Routines",
                    fontSize = MaterialTheme.typography.displaySmall.fontSize,
                    fontWeight = FontWeight.W900,
                    color = black
                )

                Button(

                    modifier = Modifier
                        .height(40.dp)
                        .wrapContentWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = black),
                    onClick = onAddRoutine,

                ) {
                    Text(
                        text = "Create +",
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = primary
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(listCardHeight.dp)
                    .background(
                        color = black,
                        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                    )
            ) {

                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {



                    items(items = viewModel.routineList) {routine->
                        RoutineCard(

                            routineName = routine.name,
                            exerciseCount = routine.exercise.size,
                            onCardClick = {},
                            onStartNowClick = {},
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                    }

                }
            }
        }




}


@Preview
@Composable
fun RoutinesScreenPreview() {
    RoutineListScreen(onRoutineClicked = { i,g->
    }, onAddRoutine = {})
}