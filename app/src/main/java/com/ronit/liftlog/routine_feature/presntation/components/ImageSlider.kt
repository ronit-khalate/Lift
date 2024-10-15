package com.ronit.liftlog.routine_feature.presntation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ronit.liftlog.core.di.RetrofitModule
import com.ronit.liftlog.ui.theme.primary


@Composable
fun ImageSlider(
    modifier: Modifier = Modifier,
    uriList:List<String>
) {

    Box(
        modifier = modifier
    ){

        val pagerState = rememberPagerState {
            uriList.size
        }

        val dotState = rememberPagerState {
            uriList.size
        }



        HorizontalPager(
            state = pagerState,
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                alignment = Alignment.Center,

                contentScale = ContentScale.Crop,
                model = "${RetrofitModule.provideBaseUrl()}exercises/${uriList[it]}",
                contentDescription = null,

                )
        }




    }
}

@Preview
@Composable
private fun ImageSliderPreview() {


}


@Composable
private fun FilledDot(modifier: Modifier = Modifier) {




        Canvas(modifier = Modifier
            .size(10.dp)
        ) {

            drawCircle(primary)
        }

}

@Preview("Filled Dot Preview")
@Composable
private fun FilledDotPreview() {

    FilledDot()
}