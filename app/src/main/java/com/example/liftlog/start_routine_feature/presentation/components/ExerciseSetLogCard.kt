package com.example.liftlog.start_routine_feature.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.liftlog.core.data.model.ExerciseLog
import com.example.liftlog.core.data.model.Set
import com.example.liftlog.ui.theme.black
import com.example.liftlog.ui.theme.textGray
import com.example.liftlog.ui.theme.white
import io.realm.kotlin.ext.realmListOf


@Composable
fun ExerciseSetLogCard(
    modifier: Modifier = Modifier,
    count:Int,
    exerciseLog:ExerciseLog,
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
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "${count}.${exerciseLog.exercise?.name}",
                    color = white,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            /// Header

            ExerciseSetLogCardHeader(
                setTextWidth = setTextWidth,
                kgTextWidth = kgTextWidth,
                repsTextWidth = repsTextWidth
            )


            // Header End



            // Data

            exerciseLog.setList.forEachIndexed {index, set->


                ExerciseSetLogData(
                    setTextWidth = setTextWidth,
                    kgTextWidth = kgTextWidth,
                    repsTextWidth = repsTextWidth,
                    set = set,
                    exId = exerciseLog._id.toHexString(),
                    index=index + 1,
                    updateWeight = updateWeight,
                    updateReps = updateReps,
                    updateNotes = updateNotes
                )
            }
            // Data End

            Spacer(modifier = Modifier.height(15.dp))



            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),

                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = white),
                onClick = { onAddSetBtnClick(exerciseLog._id.toHexString()) }
            ) {

                Text(
                    text = " + Add Set",
                    fontSize = 12.sp,
                    color = black
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
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .weight(30f),
        ){

            Column(
                modifier = Modifier
                    .wrapContentWidth()

                ,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    modifier = Modifier
                        .onGloballyPositioned {
                            setTextWidth.value= it.size.width
                        },
                    text = "SET",
                    color = textGray,
                    fontSize = 10.sp
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
                        .onGloballyPositioned {
                            kgTextWidth.value= it.size.width
                        },
                    text = "KG",
                    color = textGray,
                    fontSize = 10.sp
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
                    modifier = Modifier,
                    text = "REPS",
                    color = textGray,
                    fontSize = 10.sp
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
                    modifier = Modifier,
                    text = "NOTES",
                    color = textGray,
                    fontSize = 10.sp
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
    set: Set,
    index: Int
) {
    Row (
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
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
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {


                    BasicTextField(
                        modifier = Modifier
                            .padding(vertical = 5.dp),
                        textStyle = TextStyle(color = white , fontSize = 10.sp),

                        value = "$index",
                        onValueChange = {},
                        decorationBox = {

                            Row(
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .wrapContentWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                it()
                            }
                        }
                    )

                }
            }


            // Weight
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(30f),
                horizontalAlignment = Alignment.Start,

                ) {

                Column(
                    modifier = Modifier
                        .wrapContentSize(),


                    ) {

                    BasicTextField(
                        modifier = Modifier
                            .padding(vertical = 5.dp),
                        textStyle = TextStyle(color = white , fontSize = 10.sp),

                        value = if(set.weight.toString() =="0.0") "" else set.weight.toString(),
                        cursorBrush = SolidColor(white),
                        onValueChange = { updateWeight(set._id.toHexString(),exId,it) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        decorationBox = {

                            Row(
                                modifier = Modifier
                                    .wrapContentWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                it()
                            }
                        }
                    )

                }
            }


            // Resp
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(30f),
                horizontalAlignment = Alignment.Start,

                ) {

                Column(
                    modifier = Modifier
                        .wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        textStyle = TextStyle(color = white , fontSize = 10.sp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = set.repetitions.toString(),
                        onValueChange = {updateReps(set._id.toHexString(),exId,it)},
                        cursorBrush = SolidColor(white),

                        decorationBox = {

                            Row(
                                modifier = Modifier
                                    .wrapContentWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                it()
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
                        .padding(vertical = 5.dp),
                    textStyle = TextStyle(color = white , fontSize = 10.sp),

                    value = set.notes,
                    onValueChange = {updateNotes(set._id.toHexString(),exId , it)},
                    cursorBrush = SolidColor(white),

                    decorationBox = {

                        Row(
                            modifier = Modifier
                                .wrapContentWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            it()
                        }
                    }

                )





            }
        }


    }
}



val b = black.toArgb().toLong()
@Preview
@Composable
private fun ExerciseSetLogCardPreview() {

    ExerciseSetLogCard(
        count = 1,
        exerciseLog = ExerciseLog().apply {
            this.setList = realmListOf(
                Set().apply {
                    this.notes = "start form 30"
                    this.weight="43.0F"
                    this.repetitions="5"
                },
                Set().apply {
                    this.notes = "start form 30"
                    this.weight= "43.0F"
                    this.repetitions="5"
                },

                Set().apply {
                    this.notes = "start form 30"
                    this.weight= "43.0F"
                    this.repetitions="5"
                },
            )

        },
        onAddSetBtnClick = {},
        updateWeight = {id,ex,data ->},
        updateReps = {id,ex,data ->},
        updateNotes = {id,ex,data ->},

    )
}