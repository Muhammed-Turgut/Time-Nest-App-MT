package com.muhammedturgut.timenestapp.Screens

import GoalsScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muhammedturgut.timenestapp.ModelClass.Item
import com.muhammedturgut.timenestapp.R
import com.muhammedturgut.timenestapp.ViewModel.GolasMissionViewModel
import com.muhammedturgut.timenestapp.ui.theme.ligthGray
import com.muhammedturgut.timenestapp.ui.theme.ligthGray2
import com.muhammedturgut.timenestapp.ui.theme.navTopSelectedColor
import com.muhammedturgut.timenestapp.ui.theme.transparan




@Composable
fun ToDoScreen(navController: NavHostController,item: List<Item>,saveFunction : (item:Item ) ->Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column {
                Text(
                    text = "Time Nest",
                    fontFamily = FontFamily(Font(R.font.righteousregular)),
                    fontSize = 24.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 16.dp, bottom = 8.dp), textAlign = TextAlign.Start
                )


            }
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                NavigationTopBar(navController = navController) // Navigation bar'ı burada çağırıyoruz
            }


                // Navigation graph'ını burada oluşturuyoruz.
                NavHost(navController = navController, startDestination = "devam_ediyor") {
                    composable("devam_ediyor") { Golas(item,saveFunction) }
                    composable("tamamlandi") { Tamamlandı() }
                }


        }
    }
}

@Composable
fun NavigationTopBar(navController: NavHostController) {
    val itemList = listOf(
        CategoriHostItem("Hedefler", "devam_ediyor"),
        CategoriHostItem("Başarılar", "tamamlandi")
    )

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(ligthGray)
            .size(height = 50.dp, width = 230.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        var selectedTab by remember { mutableStateOf(itemList.first().route) }

        itemList.forEach { item ->
            Text(
                modifier = Modifier
                    .width( if(item.route == selectedTab) 130.dp else 100.dp)
                    .clickable {
                        navController.navigate(item.route)
                        selectedTab = item.route
                    }
                    .padding(6.dp)
                    .clip(if (item.route == selectedTab) RoundedCornerShape(8.dp) else RoundedCornerShape(0.dp))
                    .background(if (item.route == selectedTab) navTopSelectedColor else transparan).padding(8.dp),
                textAlign = TextAlign.Center,
                text = item.title,
                fontSize = if (item.route == selectedTab) 18.sp else 14.sp,
                color = if (item.route == selectedTab) Color.White else ligthGray2,

            )
        }

    }
}

@Composable
fun Golas(item: List<Item>,saveFunction: (item:Item ) -> Unit) {

    println("Golas Screen")

    GoalsScreen(item,saveFunction)
}

@Composable
fun Tamamlandı() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Tamamlandı Ekranı", fontSize = 24.sp)
    }
}

data class CategoriHostItem(
    val title: String,
    val route: String
)
