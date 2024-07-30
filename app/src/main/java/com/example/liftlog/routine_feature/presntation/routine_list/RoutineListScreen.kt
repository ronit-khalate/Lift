package com.example.liftlog.routine_feature.presntation.routine_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.liftlog.core.presentation.component.ClickableRow
import com.example.liftlog.core.presentation.component.ThreeSectionTopBar


@Composable
fun RoutineListScreen(
    modifier: Modifier = Modifier,
    onAddRoutine:()->Unit
){

    val viewModel  = hiltViewModel<RoutineListScreenViewModel>()

    Scaffold(

        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),

        topBar = {
            ThreeSectionTopBar(
                modifier = Modifier
                    .height(55.dp),

                leftContent = {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Edit")
                    }
                },

                rightContent = {
                    IconButton(onClick = onAddRoutine) {
                        Image(
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Routine"
                        )
                    }
                }
            )
        }
    ) { it ->

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, bottom = 4.dp)

        ) {



            // Routine Name
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Routines"
                )
            }

//            Spacer(modifier = Modifier.height(10.dp))


            LazyColumn(
                modifier = Modifier

                    .fillMaxSize()
            ) {



                items(items = viewModel.routineList) {routine->
                    ClickableRow(height = 50, text = routine.name, onClick = { /*TODO*/ }) {
                        Image(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
                        )
                    }
                }

            }
        }
    }

}


@Preview
@Composable
fun RoutinesScreenPreview() {
    RoutineListScreen(){}
}