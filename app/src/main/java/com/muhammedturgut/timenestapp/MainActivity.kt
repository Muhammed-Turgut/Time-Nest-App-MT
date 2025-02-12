package com.muhammedturgut.timenestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muhammedturgut.timenestapp.ui.theme.PrimaryColor
import com.muhammedturgut.timenestapp.ui.theme.TimeNestAppTheme
import com.muhammedturgut.timenestapp.ui.theme.selectedIconColor

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unSelectedIcon: Int,
    val route: String,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeNestAppTheme {
                val navController = rememberNavController()
                Surface {
                    Scaffold(
                        bottomBar = {
                            NavigationBottomBar(navController)
                        },
                        content = { paddingValues ->
                            NavigationHost(navController, Modifier.padding(paddingValues))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = "todo", modifier = modifier) {
        composable("todo") { TodoScreen() }
        composable("calendar") { CalendarScreen() }
        composable("timer") { TimerScreen() }
    }
}

@Composable
fun TodoScreen() {
    ScreenContent("Yapılacaklar Ekranı")
}

@Composable
fun CalendarScreen() {
    ScreenContent("Takvim Ekranı")
}

@Composable
fun TimerScreen() {
    ScreenContent("Zamanlayıcı Ekranı")
}

@Composable
fun ScreenContent(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Time Nest",
                fontFamily = FontFamily(Font(R.font.righteousregular)),
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(20.dp, 24.dp, 8.dp, 0.dp)
            )

        }
    }
}

@Composable
fun NavigationBottomBar(navController: NavHostController) {
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
    NavigationBar(containerColor = PrimaryColor) {
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
                    Text(text = item.title, fontSize = 12.sp)
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
        TodoScreen()
    }
}