package com.muhammedturgut.timenestapp.Activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass.BottomNavigationItem
import com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass.Item
import com.muhammedturgut.timenestapp.R
import com.muhammedturgut.timenestapp.TimerScreen.Model.TimerItem
import com.muhammedturgut.timenestapp.TimerScreen.ViewModel.TimerViewModel
import com.muhammedturgut.timenestapp.ToDo.Screens.ToDoScreen
import com.muhammedturgut.timenestapp.ToDo.Screens.ViewModel.GolasMissionViewModel
import com.muhammedturgut.timenestapp.ui.theme.PrimaryColorWhite
import com.muhammedturgut.timenestapp.ui.theme.TimeNestAppTheme



class MainActivity : ComponentActivity() {
    private val viewModelToDo: GolasMissionViewModel by viewModels()
    private val viewModelTimer: TimerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            TimeNestAppTheme {
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
                        composable("calendar") { CalendarScreen() }
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
fun CalendarScreen() {
    com.muhammedturgut.timenestapp.CalendarScreen()
}

@Composable
fun TimerScreen(item: List<TimerItem>, saveFunction: (TimerItem) -> Unit,deleteFunction: (TimerItem) -> Unit,updateFunction: (TimerItem) -> Unit) {
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
            title = "Takvim",
            selectedIcon = R.drawable.selectedwhitecalendar,
            unSelectedIcon = R.drawable.defaultcalendar,
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TimeNestAppTheme {

    }
}