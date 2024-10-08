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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ronit.liftlog.R
import com.ronit.liftlog.routine_feature.presntation.routine.components.SelectedIdentifier
import com.ronit.liftlog.start_routine_feature.data.model.ExerciseLogDto
import com.ronit.liftlog.start_routine_feature.data.model.SetDto
import com.ronit.liftlog.ui.theme.black
import com.ronit.liftlog.ui.theme.body
import com.ronit.liftlog.ui.theme.neutral
import com.ronit.liftlog.ui.theme.primary
import com.ronit.liftlog.ui.theme.primaryText
import com.ronit.liftlog.ui.theme.tertiary


@Composable
fun ExerciseSetLogCard(
    modifier: Modifier = Modifier,
    exerciseLog:ExerciseLogDto,
    onAddSetBtnClick:(id:String)->Unit,
    updateWeight: (id:String,exLogId:String,data:String)->Unit,
    updateReps: (id:String,exLogId:String,data:String)->Unit,
    updateNotes: (id:String,exLogId:String,data:String)->Unit,
) {


        var setTextWidth = remember { mutableStateOf(0) }
        var kgTextWidth = remember { mutableStateOf(0) }
        var repsTextWidth = remember { mutableStateOf(0) }

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
                            shape = RectangleShape
                        )

                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "${exerciseLog.name}",
                            color = primaryText,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
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




                /// Header

                ExerciseSetLogCardHeader(
                    setTextWidth = setTextWidth,
                    kgTextWidth = kgTextWidth,
                    repsTextWidth = repsTextWidth
                )


                // Header End


                // Data

                exerciseLog.setList.forEachIndexed { index, set ->


                    ExerciseSetLogData(
                        setTextWidth = setTextWidth,
                        kgTextWidth = kgTextWidth,
                        repsTextWidth = repsTextWidth,
                        set = set,
                        exId = exerciseLog.id.toHexString(),
                        index = index + 1,
                        updateWeight = updateWeight,
                        updateReps = updateReps,
                        updateNotes = updateNotes
                    )


                }
                // Data End


            }



            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),

                shape = RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = tertiary),
                onClick = { onAddSetBtnClick(exerciseLog.id.toHexString()) }
            ) {

                Text(
                    text = "Add Set",
                    fontSize = 12.sp,
                    color = primaryText
                )
            }



        }

}





@Composable
private fun ExerciseSetLogCardHeader(
    modifier: Modifier = Modifier,
    setTextWidth:MutableState<Int>,
    kgTextWidth:MutableState<Int>,
    repsTextWidth:MutableState<Int>

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

                        .padding(8.dp)
                        .onGloballyPositioned {
                            setTextWidth.value = it.size.width
                        },
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
                        .padding(8.dp)
                        .onGloballyPositioned {
                            kgTextWidth.value = it.size.width
                        },
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
                    .onGloballyPositioned {
                        repsTextWidth.value = it.size.width
                    }
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
    setTextWidth: MutableState<Int>,
    kgTextWidth: MutableState<Int>,
    repsTextWidth: MutableState<Int>,
    updateWeight: (id: String,exLogId:String, data: String) -> Unit,
    updateReps: (id: String, exLogId: String, data: String) -> Unit,
    updateNotes: (id: String, exLogId: String, data: String) -> Unit,
    exId:String,
    set: SetDto,
    index: Int
) {
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
                        .padding(vertical = 8.dp, horizontal = 8.dp)
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

                        .padding(vertical = 8.dp, horizontal = 8.dp),


                    ) {


                    BasicTextField(
                        modifier = Modifier,
                        textStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold , color = primaryText),

                        value = if(set.weight=="0.0") "" else set.weight,
                        singleLine = true,
                        cursorBrush = SolidColor(primaryText),
                        onValueChange = { updateWeight(set.id.toHexString(),exId,it) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        decorationBox = {

                            Box(
                                modifier = Modifier,
                            ) {
                                it()
                                if(set.weight.isBlank()){

                                    Text(
                                        text = set.prevWeight.ifBlank { "0" },
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
                        .padding(vertical = 8.dp, horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold , color = primaryText),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        value = set.repetitions.toString(),
                        onValueChange = {updateReps(set.id.toHexString(),exId,it)},
                        cursorBrush = SolidColor(primaryText),

                        decorationBox = {

                            Box(
                                modifier = Modifier
                                    .wrapContentWidth(),
                            ) {
                                it()
                                if(set.repetitions.isBlank()){

                                    Text(
                                        text = set.prevRepetitions.ifBlank { "0" },
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
                        .padding(vertical = 8.dp, horizontal = 8.dp),
                    textStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold , color = primaryText),

                    value = set.notes,
                    onValueChange = {updateNotes(set.id.toHexString(),exId , it)},
                    cursorBrush = SolidColor(primaryText),

                    decorationBox = {
                        Box(
                            modifier = Modifier
                                .wrapContentWidth(),
                        ) {

                            if(set.notes.isBlank()){

                                Text(
                                    text = set.prevNotes,
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

    ExerciseSetLogCard(
        exerciseLog = ExerciseLogDto(
            exerciseID = "",
            name = "Chest",
            note = "",
            muscleGroup = "asd",
            setList = mutableStateListOf(
                SetDto(
                    exerciseId = "",

                )
            )
        ),
        onAddSetBtnClick = {},
        updateWeight = {id,ex,data ->},
        updateReps = {id,ex,data ->},
        updateNotes = {id,ex,data ->},

    )
}