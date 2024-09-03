package com.example.liftlog.core.presentation.component

import androidx.annotation.DimenRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sin


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    height: Dp = 40.dp
) {




    Row(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(30.dp))
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(50.dp)
            ),
        verticalAlignment = Alignment.CenterVertically

    ) {


        Spacer(modifier = Modifier.width(9.dp))
        Box(
            modifier=Modifier

                .weight(1f),

            contentAlignment = Alignment.Center
        ) {

            Image(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize()
                    .padding(horizontal = 2.dp, vertical = 1.dp,)
                    .align(Alignment.Center),
                imageVector = Icons.TwoTone.Search ,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onTertiary)
            )
        }

        BasicTextField(
            value = "",
            onValueChange ={ TODO("Implement Search Text value change") },
            modifier = Modifier
                .weight(10f)
                .padding(5.dp)
                .fillMaxSize(),



            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,


            ),

            decorationBox = {


            },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {TODO("implement on search")})
        )

    }

}


@Preview(
    "SearchBar Preview",
    showBackground = true,
    showSystemUi = true
)
@Composable()
fun SearchBarPreview() {

    SearchBar()

}