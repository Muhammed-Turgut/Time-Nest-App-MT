package com.muhammedturgut.timenestapp.ToDo.Screens

import GoalsScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass.Item
import com.muhammedturgut.timenestapp.R
import com.muhammedturgut.timenestapp.ui.theme.GolaScreenTopBarSelected
import com.muhammedturgut.timenestapp.ui.theme.ligthGray
import com.muhammedturgut.timenestapp.ui.theme.ligthGray2
import com.muhammedturgut.timenestapp.ui.theme.navTopSelectedColor
import com.muhammedturgut.timenestapp.ui.theme.transparan




@Composable
fun ToDoScreen(
    navController: NavHostController,
    item: List<Item>,
    itemAim: List<Item>,
    saveFunction: (Item) -> Unit,
    UpdateFuncition: (Item) -> Unit,
    deleteItem: (Item) -> Unit,
    onTabSelected: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onTertiaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                .background(MaterialTheme.colorScheme.onPrimary)


            ) {
                Text(
                    text = "Görevler",
                    fontFamily = FontFamily(Font(R.font.righteousregular)),
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 16.dp, bottom = 8.dp), textAlign = TextAlign.Start
                )


            }
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                NavigationTopBar(
                    navController = navController,
                    onTabSelected = onTabSelected
                )
            }


                // Navigation graph'ını burada oluşturuyoruz.
                NavHost(navController = navController, startDestination = "devam_ediyor") {
                    composable("devam_ediyor") { Golas(item, saveFunction, UpdateFuncition, deleteItem) }
                    composable("tamamlandi") { Aim(itemAim, UpdateFuncition, deleteItem) }
                }


        }
    }
}

@Composable
fun NavigationTopBar(
    navController: NavHostController,
    onTabSelected: (String) -> Unit
) {
    val itemList = listOf(
        CategoriHostItem(
            "Hedefler",
            "devam_ediyor",
            R.drawable.goalsiconselected,
            R.drawable.goalsicondefault
        ),
        CategoriHostItem(
            "Tamamlandı",
            "tamamlandi",
            R.drawable.aimiconselected,
            R.drawable.aimicondefualt
        )
    )

    var selectedTab by remember { mutableStateOf(itemList.first().route) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp) // Top padding for overall row
            .height(90.dp), // Set a fixed height for the navigation bar
        horizontalArrangement = Arrangement.SpaceEvenly, // Distribute items evenly
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemList.forEach { item ->
            // Tab Row
            Row(
                modifier = Modifier
                    .background(
                        color = if (selectedTab == item.route) GolaScreenTopBarSelected else MaterialTheme.colorScheme.onTertiaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable {
                        navController.navigate(item.route)
                        selectedTab = item.route
                        onTabSelected(item.route)
                    }
                    .padding(vertical = 8.dp, horizontal = 12.dp), // Adjust padding
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center // Center items inside the row
            ) {
                // Tab Icon
                Image(
                    painter = painterResource(
                        id = if (item.route == selectedTab) item.selectedIcon else item.unSelectedIcon
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 6.dp) // Space between icon and text
                        .size(24.dp) // Set a consistent icon size
                )

                // Tab Text
                Text(
                    text = item.title,
                    textAlign = TextAlign.Center,
                    fontSize = if (item.route == selectedTab) 18.sp else 14.sp,
                    color = if (item.route == selectedTab) Color.White else MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(horizontal = 8.dp) // Padding for the text
                        .widthIn(min = 80.dp) // Ensure minimum width for text alignment
                )
            }
        }
    }
}


@Composable
fun Golas(item: List<Item>, saveFunction: (item: Item) -> Unit, UpdateFuncition: (Item) -> Unit, deleteItem: (Item) -> Unit) {

    println("Golas Screen")

    GoalsScreen(item,saveFunction, UpdateFuncition,deleteItem)
}

@Composable
fun Aim(item: List<Item>, UpdateFuncition: (Item) -> Unit, deleteItem: (Item) -> Unit) { //Yapılacakalr ekranınmızın tamamlanan görevlerini içeriyor.
 AimScreen(item,UpdateFuncition,deleteItem)
}

data class CategoriHostItem(
    val title: String,
    val route: String,
    val selectedIcon: Int,
    val unSelectedIcon: Int,
)
