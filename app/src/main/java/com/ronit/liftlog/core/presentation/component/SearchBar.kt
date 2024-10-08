package com.ronit.liftlog.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ronit.liftlog.ui.theme.body
import com.ronit.liftlog.ui.theme.neutral
import com.ronit.liftlog.ui.theme.primaryText


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query:String,
    label:String,
    onQueryEntered:(String)->Unit,
    onSearch:() ->Unit,
    height: Dp = 40.dp
) {


    Row(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .background(
                color = neutral,
            ),
        verticalAlignment = Alignment.CenterVertically

    ) {


        Spacer(modifier = Modifier.width(9.dp))
        Box(
            modifier=Modifier
                .weight(1f)
               ,

            contentAlignment = Alignment.Center
        ) {

            Image(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize()
                    .background(
                        color = neutral,
                        shape = MaterialTheme.shapes.large
                    )
                    .clickable { onSearch() }
                    .padding(horizontal = 2.dp, vertical = 1.dp,)
                    .align(Alignment.Center),
                imageVector = Icons.TwoTone.Search ,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = body)
            )
        }

        BasicTextField(
            value = query,
            onValueChange =onQueryEntered,
            modifier = Modifier
                .weight(10f)
                .padding(horizontal = 5.dp),


            cursorBrush = SolidColor(primaryText),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = primaryText),
            singleLine = true,

            decorationBox = {

                Box (
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ){

                    it()
                    if(query.isBlank() || query.isEmpty()){
                        Text(
                            text = label,
                            style =MaterialTheme.typography.bodyLarge,
                            color = body
                        )
                    }

                }



            },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {onSearch()})
        )

    }

}


@Preview(
    "SearchBar Preview",
    showBackground = true,
)
@Composable()
fun SearchBarPreview() {

    SearchBar(
        query = "",
        onQueryEntered = {},
        onSearch = {},
        label = "Search Exercise"
    )

}