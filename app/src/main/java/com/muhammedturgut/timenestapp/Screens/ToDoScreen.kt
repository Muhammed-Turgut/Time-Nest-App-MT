package com.muhammedturgut.timenestapp.Screens

import android.content.ClipData.Item
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muhammedturgut.timenestapp.Activitys.TodoScreen
import com.muhammedturgut.timenestapp.R
import com.muhammedturgut.timenestapp.ui.theme.ligthGray
import com.muhammedturgut.timenestapp.ui.theme.ligthGray2
import com.muhammedturgut.timenestapp.ui.theme.navTopSelectedColor
import com.muhammedturgut.timenestapp.ui.theme.transparan
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import org.w3c.dom.Text




@Composable
fun ToDoScreen(navController: NavHostController) {
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
                    .padding(20.dp, 24.dp, 8.dp, 16.dp)
            )

            NavigationBottomBar(navController = navController) // Navigation bar'ı burada çağırıyoruz

            // Navigation graph'ını burada oluşturuyoruz.
            NavHost(navController = navController, startDestination = "devam_ediyor") {
                composable("devam_ediyor") { DevamEdiyor() }
                composable("tamamlandi") { Tamamlandı() }
            }
        }
    }
}

@Composable
fun NavigationBottomBar(navController: NavHostController) {
    val itemList = listOf(
        KategoriHostItem("Devam Ediyor", "devam_ediyor"),
        KategoriHostItem("Tamamlandı", "tamamlandi")
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(ligthGray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        var selectedTab by remember { mutableStateOf(itemList.first().route) }

        itemList.forEach { item ->
            Text(
                modifier = Modifier
                    .clickable {
                        navController.navigate(item.route)
                        selectedTab = item.route
                    }
                    .padding(4.dp)
                    .clip(if (item.route == selectedTab) RoundedCornerShape(24.dp) else RoundedCornerShape(0.dp))
                    .background(if (item.route == selectedTab) navTopSelectedColor else transparan).padding(12.dp),
                text = item.title,
                fontSize = if (item.route == selectedTab) 18.sp else 16.sp,
                color = if (item.route == selectedTab) Color.White else ligthGray2
            )
        }

    }
}

@Composable
fun DevamEdiyor() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Devam Ediyor Ekranı", fontSize = 24.sp)
    }
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

data class KategoriHostItem(
    val title: String,
    val route: String
)
