package com.ronit.liftlog.core.presentation.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ronit.liftlog.R
import com.ronit.liftlog.core.navigation.Screen.Screens
import com.ronit.liftlog.ui.theme.black
import com.ronit.liftlog.ui.theme.neutral
import com.ronit.liftlog.ui.theme.primaryText
import com.ronit.liftlog.ui.theme.tertiary


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
                    tint = if(currentRoute == Screens.RoutineListScreen.route) primaryText else tertiary,
                    painter = painterResource(id = R.drawable.routine_icon),
                    contentDescription =""
                )
            }
        )

        NavigationBarItem(
            modifier = Modifier,
            selected = false,
            onClick = { navController.navigate(Screens.LogScreen.route)},

            colors = NavigationBarItemDefaults.colors(selectedIconColor = primaryText, unselectedIconColor = neutral,),
            icon = {
                Icon(
                    modifier = Modifier,
                    tint = if(currentRoute == Screens.LogScreen.route) primaryText else tertiary,
                    painter = painterResource(id = R.drawable.vector),
                    contentDescription =""
                )
            }
        )

        NavigationBarItem(
            modifier = Modifier,
            selected = false ,
            onClick = { /*TODO*/ },

            colors = NavigationBarItemDefaults.colors(selectedIconColor = primaryText, unselectedIconColor = neutral,),
            icon = {
                Icon(
                    modifier = Modifier,
                    tint = if(currentRoute == Screens.StartScreen.route) primaryText else tertiary,
                    painter = painterResource(id = R.drawable.stat_icon),
                    contentDescription =""
                )
            }
        )
        NavigationBarItem(
            modifier = Modifier,
            selected = false ,
            onClick = { /*TODO*/ },

            colors = NavigationBarItemDefaults.colors(selectedIconColor = primaryText, unselectedIconColor = neutral,),
            icon = {
                Icon(
                    modifier = Modifier,
                    tint = if(currentRoute == Screens.ProfileScreen.route) primaryText else tertiary,
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