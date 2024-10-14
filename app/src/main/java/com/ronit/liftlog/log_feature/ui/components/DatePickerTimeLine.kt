package com.ronit.liftlog.log_feature.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ronit.liftlog.ui.theme.backgroundDark
import com.ronit.liftlog.ui.theme.body
import com.ronit.liftlog.ui.theme.neutral
import com.ronit.liftlog.ui.theme.primary
import com.ronit.liftlog.ui.theme.primaryText
import com.ronit.liftlog.ui.theme.secondaryContainerLight
import java.time.LocalDate
import java.time.format.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerTimeLine(
    modifier: Modifier = Modifier,
    currentDate: LocalDate,
    selectedDate:LocalDate,
    localDateList:List<LocalDate>,
    onDateClick:(LocalDate)->Unit

)
{


    val screenWidth = LocalConfiguration.current.screenWidthDp
    val dateCardWidth = screenWidth/7


    LazyRow(
        modifier = modifier
            .fillMaxWidth(),


    ) {



        items(
            items = localDateList,
            key = {it.hashCode()},

        ){dateTime->

            DateCard(
                modifier = Modifier
                    .width(dateCardWidth.dp),
                dateTime = dateTime,
                selectedDate =selectedDate,
                onClick ={ onDateClick(dateTime)}
            )

        }




    }


}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateCard(
    modifier: Modifier = Modifier,
    dateTime:LocalDate,
    selectedDate: LocalDate,
    onClick:()->Unit
) {


    var isSelected by remember {
        mutableStateOf(false)
    }


    Card(
        modifier = Modifier
            .padding(end=8.dp),
        onClick = {
            isSelected = true
            onClick()
        },
        shape = MaterialTheme.shapes.small
    ) {


        Column(
            modifier = modifier
                .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                .wrapContentWidth()

                ,
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(

                text = dateTime.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale.current.platformLocale
                ).uppercase(),
                style = MaterialTheme.typography.titleSmall
            )


            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedDate.isEqual(dateTime)) primary else body
                ),
                
                shape = MaterialTheme.shapes.small
            ) {

                Column(
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .wrapContentSize()
                ) {


                    Text(
                        text = dateTime.dayOfMonth.toString(),
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = if (selectedDate.isEqual(dateTime)) backgroundDark else primaryText,
                            fontWeight = FontWeight.Bold

                        )
                    )


                }
            }


        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "Date Card")
@Composable
private fun DateCardPreview() {

    DateCard(
        dateTime = LocalDate.now(),
        selectedDate = LocalDate.now(),
        onClick = {}
    )
}


@Preview(name = "Date Picker Time Line")
@Composable
private fun DatePickerTimeLinePreview() {

    DatePickerTimeLine(
        currentDate = LocalDate.now(),
        localDateList = listOf(
            LocalDate.of(2024, 10, 1),
            LocalDate.of(2024, 10, 2),
            LocalDate.of(2024, 10, 3),
            LocalDate.of(2024, 10, 4),
            LocalDate.of(2024, 10, 5),
            LocalDate.of(2024, 10, 6),
            LocalDate.of(2024, 10, 7),
            LocalDate.of(2024, 10, 8),
            LocalDate.of(2024, 10, 9),
        ),
        selectedDate = LocalDate.now(),
        onDateClick = {}
    )
}