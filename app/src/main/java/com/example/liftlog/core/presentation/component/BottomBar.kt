package com.example.liftlog.core.presentation.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.liftlog.R
import com.example.liftlog.core.navigation.Screen.Screens
import com.example.liftlog.ui.theme.black
import com.example.liftlog.ui.theme.neutral
import com.example.liftlog.ui.theme.primary
import com.example.liftlog.ui.theme.tertiary


@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val entry = navController.currentBackStackEntryAsState()
    val currentRoute = entry.value?.destination?.route
    NavigationBar(

        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 15.dp , topEnd = 15.dp)),
        containerColor = neutral,


    ) {


        NavigationBarItem(
            modifier = Modifier,
            selected =false ,
            onClick = {
                navController.navigate(route = Screens.RoutineListScreen.route){
                    popUpTo(route = Screens.RoutineListScreen.route){
                        inclusive = true
                    }
                }
          },

            colors = NavigationBarItemDefaults.colors(selectedIconColor = black, unselectedIconColor = neutral,),
            icon = {
                Icon(
                    modifier = Modifier,
                    tint = if(currentRoute == Screens.RoutineListScreen.route) primary else tertiary,
                    painter = painterResource(id = R.drawable.routine_icon),
                    contentDescription =""
                )
            }
        )

        NavigationBarItem(
            modifier = Modifier,
            selected = false,
            onClick = { /*TODO*/ },

            colors = NavigationBarItemDefaults.colors(selectedIconColor = primary, unselectedIconColor = neutral,),
            icon = {
                Icon(
                    modifier = Modifier,
                    tint = if(currentRoute == Screens.LogScreen.route) primary else tertiary,
                    painter = painterResource(id = R.drawable.vector),
                    contentDescription =""
                )
            }
        )

        NavigationBarItem(
            modifier = Modifier,
            selected = false ,
            onClick = { /*TODO*/ },

            colors = NavigationBarItemDefaults.colors(selectedIconColor = primary, unselectedIconColor = neutral,),
            icon = {
                Icon(
                    modifier = Modifier,
                    tint = if(currentRoute == Screens.StartScreen.route) primary else tertiary,
                    painter = painterResource(id = R.drawable.stat_icon),
                    contentDescription =""
                )
            }
        )
        NavigationBarItem(
            modifier = Modifier,
            selected = false ,
            onClick = { /*TODO*/ },

            colors = NavigationBarItemDefaults.colors(selectedIconColor = primary, unselectedIconColor = neutral,),
            icon = {
                Icon(
                    modifier = Modifier,
                    tint = if(currentRoute == Screens.ProfileScreen.route) primary else tertiary,
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription =""
                )
            }
        )





    }
}

@Preview
@Composable
private fun BottomBarPreview() {
    BottomBar(navController = rememberNavController())
}