package com.chomp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.chomp.feature.details.DetailsScreen
import com.chomp.feature.homeList.HomeListScreen
import com.chomp.feature.login.LoginScreen
import kotlinx.serialization.Serializable


sealed class NavItem {
    @Serializable
    data object Login : NavItem()
    @Serializable
    data object HomeList : NavItem()
    @Serializable
    data class ItemDetails(val id: Int): NavItem()
}

@Composable
fun ChompNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startScreen: NavItem = NavItem.Login
) {
    /**
     * how do we use jetpack navigation in a
     * multi-module project?
     * 1 navigation controller per module?
     * or do we expose screen composables from other modules
     * and use this, global nav host?
     * */
    NavHost(
        navController = navController,
        startDestination = startScreen,
        modifier = modifier
    ) {


        // can we not pass on the entire nav controller to screens ?

        composable<NavItem.Login> { LoginScreen(navController) }

        composable<NavItem.HomeList> { HomeListScreen(navController) }

        composable<NavItem.ItemDetails> {
            val args = it.toRoute<NavItem.ItemDetails>()
            DetailsScreen(
                navController = navController,
                id = args.id
            )
        }

    }

}