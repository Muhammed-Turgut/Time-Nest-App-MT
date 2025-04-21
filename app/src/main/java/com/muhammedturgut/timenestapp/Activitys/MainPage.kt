package com.muhammedturgut.timenestapp.Activitys

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemChain
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ViewModel.ChainViewModel
import com.muhammedturgut.timenestapp.R
import com.muhammedturgut.timenestapp.TimerScreen.Model.TimerItem
import com.muhammedturgut.timenestapp.TimerScreen.ViewModel.TimerViewModel
import com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass.BottomNavigationItem
import com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass.Item
import com.muhammedturgut.timenestapp.ToDo.Screens.Screens.ToDoScreen
import com.muhammedturgut.timenestapp.ToDo.Screens.ViewModel.GolasMissionViewModel
import com.muhammedturgut.timenestapp.ui.theme.PrimaryColorWhite

@Composable
fun MainPage(viewModelToDo:GolasMissionViewModel,viewModelChain: ChainViewModel,viewModelTimer: TimerViewModel,navController: NavHostController){
    val navControllerBottom = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBottomBar(navControllerBottom)
        }
    ) { paddingValues ->
        NavHost(
            navController = navControllerBottom,
            startDestination = "todo",
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            composable("todo") {
                LaunchedEffect(Unit) {
                    viewModelToDo.getItemList()
                }
                val itemList by viewModelToDo.itemList.collectAsState(initial = emptyList())

                LaunchedEffect(Unit) {
                    viewModelToDo.getItemListAim()
                }
                val itemListAim by viewModelToDo.itemListAim.collectAsState(initial = emptyList())

                TodoScreen(
                    itemList,
                    itemListAim,
                    saveFunction = { item ->
                        viewModelToDo.saveItem(item)
                    },
                    UpdateFuncition = { item ->
                        viewModelToDo.updateItem(item)
                    },
                    deleteItem = { item ->
                        viewModelToDo.deleteItem(item)
                    },
                    onTabSelected = { route ->
                        if (route == "tamamlandi") {
                            viewModelToDo.getItemListAim()
                        } else {
                            viewModelToDo.getItemList()
                        }
                    }
                )
            }
            composable("calendar") {
                LaunchedEffect(Unit) {
                    viewModelChain.getItemList()
                }
                val itemList by viewModelChain.itemListChain.collectAsState(initial = emptyList())

                ChainBreakingScreen(itemList,
                    saveFunction = { item-> viewModelChain.saveItem(item) },
                    delete = { item ->  viewModelChain.deleteItem(item)},
                    navController)
            }


            composable("timer") {
                LaunchedEffect(Unit) {
                    viewModelTimer.getItemList()
                }
                val itemList by viewModelTimer.itemTimerList.collectAsState(initial = emptyList())

                TimerScreen(itemList,
                    saveFunction = {item -> viewModelTimer.saveItem(item)},
                    deleteFunction = {item -> viewModelTimer.deleteItem(item)},
                    updateFunction = {item-> viewModelTimer.deleteItem(item)})}
        }
    }
}

@Composable
fun TodoScreen(item: List<Item>, itemAim: List<Item>, saveFunction: (Item) -> Unit, UpdateFuncition: (Item) -> Unit, deleteItem: (Item) -> Unit, onTabSelected: (String) -> Unit) {
    val topNavigationBar = rememberNavController()
    ToDoScreen(
        navController = topNavigationBar,
        item = item,
        itemAim,
        saveFunction = saveFunction,
        UpdateFuncition,
        deleteItem,
        onTabSelected
    )
}

@Composable
fun ChainBreakingScreen(items: List<ItemChain>, saveFunction: (ItemChain) -> Unit, delete: (ItemChain) -> Unit,navController: NavHostController) {
    com.muhammedturgut.timenestapp.ChainBreakingScreen.ChainBreakingScreen(items,saveFunction,delete,navController)
}

@Composable
fun TimerScreen(item: List<TimerItem>, saveFunction: (TimerItem) -> Unit, deleteFunction: (TimerItem) -> Unit, updateFunction: (TimerItem) -> Unit) {
    com.muhammedturgut.timenestapp.TimerScreen.TimerScreen(item,saveFunction,deleteFunction,updateFunction)
}

@Composable
fun NavigationBottomBar(navController: NavHostController) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        systemUiController.setNavigationBarColor(PrimaryColorWhite) // veya Color.Transparent
    }

    val items = listOf(
        BottomNavigationItem(
            title = "Görevler",
            selectedIcon = R.drawable.selectedwihtlist,
            unSelectedIcon = R.drawable.defaultlist,
            route = "todo",
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Alışkanlıklar",
            selectedIcon = R.drawable.aliskanliklarselected,
            unSelectedIcon = R.drawable.aliskanliklardefault,
            route = "calendar",
            hasNews = false,

            ),
        BottomNavigationItem(
            title = "zamanlayıcı",
            selectedIcon = R.drawable.selectedwihtetimer,
            unSelectedIcon = R.drawable.defaulttimer,
            route = "timer",
            hasNews = false
        ),
        BottomNavigationItem(
            title = "istatistik",
            selectedIcon = R.drawable.selectedwhitestatistics,
            unSelectedIcon = R.drawable.defaultstatistics,
            route = "timer",
            hasNews = false
        )
    )

    var selectedItemIndex by remember { mutableStateOf(0) }
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .background(Color.White),
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = {
                    Text(text = item.title, fontSize = 12.sp, color = MaterialTheme.colorScheme.tertiaryContainer)
                },
                alwaysShowLabel = false,
                icon = {
                    BadgedBox(badge = {
                        if (item.badgeCount != null) {
                            Badge {
                                Text(text = item.badgeCount.toString())
                            }
                        } else if (item.hasNews) {
                            Badge()
                        }
                    }) {
                        Image(
                            painter = painterResource(id = if (selectedItemIndex == index) item.selectedIcon else item.unSelectedIcon),
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            )
        }
    }
}