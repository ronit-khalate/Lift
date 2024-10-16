package com.ronit.liftlog.start_routine_feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ronit.liftlog.R
import com.ronit.liftlog.core.data.model.entity.Set
import com.ronit.liftlog.core.data.model.entity.Workout
import com.ronit.liftlog.routine_feature.presntation.routine.components.SelectedIdentifier
import com.ronit.liftlog.ui.theme.black
import com.ronit.liftlog.ui.theme.body
import com.ronit.liftlog.ui.theme.neutral
import com.ronit.liftlog.ui.theme.primary
import com.ronit.liftlog.ui.theme.primaryText
import com.ronit.liftlog.ui.theme.tertiary
import org.mongodb.kbson.ObjectId


@Composable
fun ExerciseSetLogCard(
    modifier: Modifier = Modifier,
    workout: Workout,
    onAddSetBtnClick:(workoutId:String)->Unit,
    updateWeight: (setId:ObjectId,workoutId: ObjectId,data:String)->Unit,
    updateReps: (setId:ObjectId,workoutId: ObjectId,data:String)->Unit,
    updateNotes: (setId:ObjectId,workoutId: ObjectId,data:String)->Unit,
) {






        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                .background(tertiary)
        ) {

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {




                Row(
                    modifier = Modifier
                        .background(black)
                        .clip(RoundedCornerShape(topStart = 10.dp))

                        .height(intrinsicSize = IntrinsicSize.Min)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Row (
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically

                    ){

                        SelectedIdentifier(
                            color = primary,
                            shape = RoundedCornerShape(topStart = 10.dp)
                        )

                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = workout.exerciseName,
                            color = primaryText,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }



                }




                /// Header

                ExerciseSetLogCardHeader()


                // Header End


                // Data

                workout.sets.forEachIndexed { index, setPair ->


                    ExerciseSetLogData(
                        set = setPair,
//                        previousSet = setPair.second,
                        index = index + 1,
                        workoutId = workout._id,
                        updateWeight = updateWeight,
                        updateReps = updateReps,
                        updateNotes = updateNotes
                    )


                }
                // Data End


            }



            HorizontalDivider(color = neutral)
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                    .background(tertiary)
                    .fillMaxWidth(),

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


                TextButton(
                    onClick ={onAddSetBtnClick(workout._id.toHexString())}
                ) {
                    Text(
                        text = "Add Set",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = primary
                    )
                }




                Row {



                    IconButton(onClick = {})
                    {

                        Icon(
                            modifier = Modifier,
                            tint = primary,
                            painter = painterResource(id = R.drawable.vector),
                            contentDescription =""
                        )

                    }

                    IconButton(onClick = {})
                    {

                        Icon(
                            modifier = Modifier,
                            tint = primary,
                            painter = painterResource(id = R.drawable.stat_icon),
                            contentDescription =""
                        )

                    }
                }

            }





        }

}





@Composable
private fun ExerciseSetLogCardHeader(
    modifier: Modifier = Modifier,

) {
    Row(
        modifier = Modifier
            .background(neutral),
        horizontalArrangement = Arrangement.Start,

    ) {
        Column(
            modifier = Modifier
                .weight(30f),
        ){

            Column(
                modifier = Modifier
                    .wrapContentWidth(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    modifier = Modifier

                        .padding(8.dp),
                    text = "SET",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold , color = primaryText)
                )
            }


        }

        Column(
            modifier = Modifier
                .weight(30f),


            ) {

            Column(
                modifier = Modifier
                    .wrapContentWidth()


                ,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "KG", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold , color = primaryText)
                )
            }


        }

        Column(
            modifier = Modifier

                .weight(30f),


            ) {

            Column(
                modifier = Modifier
                    .wrapContentWidth()
                ,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "REPS", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold , color = primaryText)
                )
            }


        }
        Column(
            modifier = Modifier
                .weight(40f),


            ) {

            Column(
                modifier = Modifier
                    .wrapContentWidth()
                ,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "NOTES", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold , color = primaryText)
                )
            }


        }
    }
}


@Composable
private fun ExerciseSetLogData(
    modifier: Modifier = Modifier,
    workoutId:ObjectId,
    updateWeight: (id: ObjectId,workoutId: ObjectId, data: String) -> Unit,
    updateReps: (id: ObjectId, workoutId: ObjectId, data: String) -> Unit,
    updateNotes: (id: ObjectId, workoutId: ObjectId, data: String) -> Unit,
    set: Set,
//    previousSet:Set,
    index: Int
) {


    val repsTextFieldFocusRequester = remember { FocusRequester() }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(tertiary),
    ){


        Row(

            horizontalArrangement = Arrangement.Start

        ) {


            // Count
            Column(
                modifier = Modifier
                    .weight(30f),
            ) {

                Column(
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 12.dp)
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {



                    Text(
                        modifier = Modifier,
                        text = index.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold , color = primaryText),
                    )

                }
            }


            // Weight
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(30f),
                horizontalAlignment = Alignment.Start,

                ) {

                Column(
                    modifier = Modifier

                        .padding(vertical = 12.dp, horizontal = 12.dp),


                    ) {


                    BasicTextField(
                        modifier = Modifier,
                        textStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold , color = primaryText),

                        value = if(set.weight=="0.0") "" else set.weight,
                        singleLine = true,
                        cursorBrush = SolidColor(primaryText),
                        onValueChange = { updateWeight(set._id,workoutId,it) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {repsTextFieldFocusRequester.requestFocus()}),
                        decorationBox = {

                            Box(
                                modifier = Modifier,
                            ) {
                                it()
                                if(set.weight.isBlank()){

                                    Text(
                                        text = "0",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold , color = body),

                                    )
                                }


                            }
                        }

                    )

                }
            }


            // Resp
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(30f),
                horizontalAlignment = Alignment.Start,

                ) {

                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(vertical = 12.dp, horizontal = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    BasicTextField(
                        modifier = Modifier
                            .focusRequester(focusRequester = repsTextFieldFocusRequester)
                            .fillMaxWidth(),
                        textStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold , color = primaryText),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        value = set.repetitions.toString(),
                        onValueChange = {updateReps(set._id,workoutId,it)},
                        cursorBrush = SolidColor(primaryText),

                        decorationBox = {

                            Box(
                                modifier = Modifier
                                    .wrapContentWidth(),
                            ) {
                                it()
                                if(set.repetitions.isBlank()){

                                    Text(
                                        text =  "0" ,
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold , color = body),

                                        )
                                }


                            }
                        }

                    )

                }
            }

            // Notes

            Column(
                modifier = Modifier
                    .weight(40f),
                horizontalAlignment = Alignment.Start,

            ){


                BasicTextField(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(vertical = 12.dp, horizontal = 12.dp),
                    textStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold , color = primaryText),

                    value = set.notes,
                    onValueChange = {updateNotes(set._id,workoutId , it)},
                    cursorBrush = SolidColor(primaryText),

                    decorationBox = {
                        Box(
                            modifier = Modifier
                                .wrapContentWidth(),
                        ) {

                            if(set.notes.isBlank()){

                                Text(
                                    text = "",
                                    style = TextStyle(color = body , fontSize = 10.sp)
                                )
                            }
                            it()


                        }
                    }

                )





            }
        }


    }
}



val b = black.toArgb().toLong()
@Preview(
    showBackground = true,
)
@Composable
private fun ExerciseSetLogCardPreview() {

//    ExerciseSetLogCard(
//
//        exerciseLog = ExerciseLogDto(
//            exerciseID = "",
//            name = "Chest",
//            note = "",
//            muscleGroup = "asd",
//            setList = mutableStateListOf(
//                SetDto(
//                    exerciseId = "",
//
//                )
//            )
//        ),
//        onAddSetBtnClick = {},
//        updateWeight = {id,ex,data ->},
//        updateReps = {id,ex,data ->},
//        updateNotes = {id,ex,data ->},
//
//    )
}