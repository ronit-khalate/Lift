package com.example.liftlog.presentation.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.liftlog.presentation.component.ClickableRow
import com.example.liftlog.presentation.component.SearchBar
import com.example.liftlog.presentation.component.ThreeSectionTopBar

/**
*
* @param onExerciseClicked use it to do something when user clicks exercise like adding it to routine
 * or opening exercise screen
* */

@Composable
fun ExerciseListScreen(
    modifier: Modifier = Modifier,
    onExerciseClicked:(exerciseId:String?)->Unit
) {


    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        topBar = {
            // Top Bar
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
    ) {
        Column(

            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, bottom = 4.dp),

            verticalArrangement = Arrangement.Top
        ) {


            Spacer(modifier = Modifier.height(10.dp))

            SearchBar()


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp, bottom = 4.dp, top = 20.dp),
            ){

                items(10){

                    ClickableRow(height = 35, text = "Ronit", onClick = {onExerciseClicked(null)}) {
                        Image(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)

                    }
                }
            }
        }
    }


}




@Preview(showBackground = true)
@Composable
private fun ExerciseListScreenPreview() {

    ExerciseListScreen(){}

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