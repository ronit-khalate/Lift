package com.ronit.liftlog.log_feature.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ronit.liftlog.core.domain.titlecase
import com.ronit.liftlog.core.navigation.Screen.Screens
import com.ronit.liftlog.core.presentation.component.BottomBar
import com.ronit.liftlog.core.presentation.component.SwipeToDeleteContainer
import com.ronit.liftlog.log_feature.ui.components.DatePickerTimeLine
import com.ronit.liftlog.log_feature.ui.components.RoutineLogCard
import com.ronit.liftlog.log_feature.ui.state.LogScreenUiState
import com.ronit.liftlog.ui.theme.black
import com.ronit.liftlog.ui.theme.primary
import com.ronit.liftlog.ui.theme.primaryText
import java.time.LocalDate


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LogScreen(
    state:LogScreenUiState,
    modifier: Modifier = Modifier,
    navController: NavController,
    onCreateRoutineClicked:() ->Unit,
    onDateClicked:(LocalDate)->Unit,
    onGoToTodaysLog:()->Unit
) {




    val scrollState = rememberScrollState()
    Scaffold(

        bottomBar = { BottomBar(navController = navController) },
        modifier = modifier
            .fillMaxSize(),
        containerColor = black
    ) {paddingValues->


        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp , vertical = 16.dp)
        ) {


            /// Top section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier.clickable { onGoToTodaysLog() },
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = state.currentDate.dayOfWeek.name.titlecase(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = primaryText,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "${state.currentDate.month.name.titlecase()} ${state.currentDate.dayOfMonth} ${state.currentDate.year.toString()}",
                        color = primaryText,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    )

                }



                OutlinedButton(
                    border = BorderStroke(width = 1.dp, color = primary),
                    onClick = onCreateRoutineClicked
                ) {

                    Text(
                        text = "Create Routine +",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = primaryText
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            DatePickerTimeLine(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                currentDate = state.currentDate,
                localDateList = state.dateList,
                selectedDate = state.selectedDate,
                onDateClick = onDateClicked
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),

            ) {


                items(items = state.logs ){
                    SwipeToDeleteContainer(

                        onDelete = { },
                        dialogTitleText = "This action cannot be undone",
                        dialogTextText = "Are you sure you want to delete this Log?",
                        content = {
                            RoutineLogCard(
                                log = it,
                                onClick = {}
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }


            }

        }
    }
}


@Preview(backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
private fun LogScreenPreview() {
    LogScreen(
        LogScreenUiState(),
        navController = rememberNavController(),
        onCreateRoutineClicked = {},
        onDateClicked = {},
        onGoToTodaysLog = {}
    )
}