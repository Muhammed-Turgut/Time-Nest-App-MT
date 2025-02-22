package com.muhammedturgut.timenestapp.Activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.muhammedturgut.timenestapp.ModelClass.BottomNavigationItem
import com.muhammedturgut.timenestapp.ModelClass.Item
import com.muhammedturgut.timenestapp.R
import com.muhammedturgut.timenestapp.Screens.ToDoScreen
import com.muhammedturgut.timenestapp.ViewModel.GolasMissionViewModel
import com.muhammedturgut.timenestapp.ui.theme.PrimaryColor
import com.muhammedturgut.timenestapp.ui.theme.TimeNestAppTheme
import com.muhammedturgut.timenestapp.ui.theme.selectedIconColor


class MainActivity : ComponentActivity() {
    private val viewModel: GolasMissionViewModel by viewModels()

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
                                viewModel.getItemList()
                            }
                            val itemList by viewModel.itemList.collectAsState(initial = emptyList())
                            TodoScreen(itemList) { item ->
                                viewModel.saveItem(item)
                            }
                        }
                        composable("calendar") { CalendarScreen() }
                        composable("timer") { TimerScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun TodoScreen(item: List<Item>, saveFunction: (Item) -> Unit) {
    val topNavigationBar = rememberNavController()
    ToDoScreen(
        navController = topNavigationBar,
        item = item,
        saveFunction = saveFunction
    )
}

@Composable
fun CalendarScreen() {
    com.muhammedturgut.timenestapp.Screens.CalendarScreen()
}

@Composable
fun TimerScreen() {
    com.muhammedturgut.timenestapp.Screens.TimerScreen()
}

@Composable
fun NavigationBottomBar(navController: NavHostController) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        systemUiController.setNavigationBarColor(PrimaryColor) // veya Color.Transparent
    }

    val items = listOf(
        BottomNavigationItem(
            title = "Yapılacaklar",
            selectedIcon = R.xml.yapilacaklarselected,
            unSelectedIcon = R.xml.yapilacaklardefualt,
            route = "todo",
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Takvim",
            selectedIcon = R.xml.takvimselected,
            unSelectedIcon = R.xml.takvimdefualt,
            route = "calendar",
            hasNews = false,

        ),
        BottomNavigationItem(
            title = "Zamanlayıcı",
            selectedIcon = R.xml.timeselected,
            unSelectedIcon = R.xml.timedefualt,
            route = "timer",
            hasNews = false
        )
    )

    var selectedItemIndex by remember { mutableStateOf(0) }
    NavigationBar(
             containerColor = PrimaryColor,
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
                    Text(text = item.title, fontSize = 12.sp, color = Color.White)
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
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = selectedIconColor,
                    selectedTextColor = selectedIconColor
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