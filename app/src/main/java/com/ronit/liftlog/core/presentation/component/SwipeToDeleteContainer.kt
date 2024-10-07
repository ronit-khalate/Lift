package com.ronit.liftlog.core.presentation.component

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import com.ronit.liftlog.ui.theme.primary
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteContainer(
    animationDuration:Int = 500,
    onDelete:() ->Unit,
    dialogTitleText:String,
    dialogTextText:String,
    content: @Composable ()->Unit
) {

    var isRemoved by remember {
        mutableStateOf(false)
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    val state = rememberSwipeToDismissBoxState(


        confirmValueChange = { value->


            if(value == SwipeToDismissBoxValue.EndToStart){

                showDialog =true

            }


            false


        },
    )






    AnimatedVisibility(
        visible = showDialog,
        enter = slideInVertically(),
        exit = scaleOut(),

    ) {
        BasicDialog(


            onConfirm = {
                isRemoved = false
                showDialog = false
                onDelete()


            },
            confirmBtnColor = Color.Red,
            onDismiss = {
                showDialog = false
            },
            dismissBtnColor = primary,
            dismissBtnText = "Cancel",
            confirmBtnText = "Delete",
            titleText = dialogTitleText,
            textString = dialogTextText
        )
    }







    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkOut(
            animationSpec = tween(animationDuration),
            shrinkTowards = Alignment.TopStart
        ) + fadeOut(),
        enter = slideInHorizontally(
            animationSpec = spring(),
        )+ fadeIn()
    )  {

            SwipeToDismissBox(
                state = state,


                backgroundContent = {
                    DeleteBackground(swipeToDismissState = state)
                },
                enableDismissFromStartToEnd = false,

                content ={
                    content()
                },

            )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    swipeToDismissState: SwipeToDismissBoxState
) {

    val color = if(swipeToDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart){
        Color.Red
    }else{
        Color.Transparent
    }

    SideEffect {
        Log.d("pro","${16} / ${swipeToDismissState.progress} = ${(16*swipeToDismissState.progress).dp} ")
    }

    val num=9F


    Box (
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .fillMaxSize()
            .background(color)
,
        contentAlignment = Alignment.CenterEnd
    ){
        Icon(

            modifier = Modifier
                .padding(end = (30*(swipeToDismissState.progress)).dp.coerceAtMost(20.dp)),
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Exercise",
            tint = Color.White
        )
    }
}