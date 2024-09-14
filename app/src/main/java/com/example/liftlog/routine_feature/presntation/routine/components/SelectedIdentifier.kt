package com.example.liftlog.routine_feature.presntation.routine.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.liftlog.ui.theme.blue

@Composable
fun SelectedIdentifier(
    color:Color
) {

    Card(

        modifier = Modifier

            .fillMaxHeight()
            .width(8.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = MaterialTheme.shapes.extraLarge

    ) {

    }
}


@Preview
@Composable
private fun SelectedIdentifierPreview() {
    SelectedIdentifier(color = blue)
}