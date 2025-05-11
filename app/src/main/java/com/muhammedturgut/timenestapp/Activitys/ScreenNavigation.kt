package com.muhammedturgut.timenestapp.Activitys

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.muhammedturgut.timenestapp.ChainBreakingScreen.DetailsScreenChainBreaking
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemChain
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemDetailChain
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ViewModel.ChainViewModel

@Composable
fun ScreenNavigation(
    modifier: Modifier = Modifier,
    items: List<ItemChain>,
    saveFunction: (ItemChain) -> Unit,
    delete: (ItemChain) -> Unit,
    viewModel: ChainViewModel,
    saveFunctionDetail: (ItemDetailChain) -> Unit

) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "ChainBreaking"
    ) {
        // Ana ekran
        composable("ChainBreaking") {
            ChainBreakingScreen(
                items = items,
                saveFunction = saveFunction,
                delete = delete,
                navController = navController,
            )
        }

        // Detay ekranı, "notId" parametresi ile
        composable(route = "DetailsChanScreen/{notId}",
            arguments = listOf(navArgument("notId") { type = NavType.IntType }
        ) ){backStackEntry->
            val notId = backStackEntry.arguments?.getInt("notId")
            DetailsScreenChainBreaking(
                navHostController = navController,
                notId = notId,
                viewModel=viewModel,
                saveFunction = {item->
                    saveFunctionDetail(item)}
            )
        }
    }
}
