package com.muhammedturgut.timenestapp.Activitys

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muhammedturgut.timenestapp.ChainBreakingScreen.DetailsScreenChainBreaking
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemChain
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemDetailChain
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ViewModel.ChainViewModel
import com.muhammedturgut.timenestapp.TimerScreen.ViewModel.TimerViewModel
import com.muhammedturgut.timenestapp.ToDo.Screens.ViewModel.GolasMissionViewModel

@Composable
fun ScreenNavigation(modifier: Modifier = Modifier,
                          viewModelToDo: GolasMissionViewModel,
                          viewModelChain: ChainViewModel,
                     viewModelTimer: TimerViewModel,
                     itemsDetails: List<ItemDetailChain>,
                     itemChain: ItemChain
)
{
    val navController = rememberNavController()


    NavHost(navController=navController, startDestination = "MainPage", builder = {

        composable("MainPage"){
            MainPage(viewModelToDo,viewModelChain,viewModelTimer,navController)
        }

        composable("DetailsChanScreen"){
            DetailsScreenChainBreaking(navController,itemsDetails,itemChain)
        }

    })
}