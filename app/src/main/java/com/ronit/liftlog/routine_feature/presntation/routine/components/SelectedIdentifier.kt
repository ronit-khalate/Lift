package com.ronit.liftlog.routine_feature.presntation.routine.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ronit.liftlog.ui.theme.blue

@Composable
fun SelectedIdentifier(
    modifier: Modifier=Modifier,
    color:Color,
    shape: Shape = MaterialTheme.shapes.extraLarge
) {

    Card(

        modifier = Modifier
            .fillMaxHeight()
            .width(8.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = shape

    ) {

    }
}


@Preview
@Composable
private fun SelectedIdentifierPreview() {
    SelectedIdentifier(color = blue)
}