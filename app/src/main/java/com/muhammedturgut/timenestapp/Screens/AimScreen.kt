package com.muhammedturgut.timenestapp.Screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammedturgut.timenestapp.ModelClass.Item
import com.muhammedturgut.timenestapp.ui.theme.GolasRowColor
import com.muhammedturgut.timenestapp.ui.theme.GolasRowColorText
import com.muhammedturgut.timenestapp.ui.theme.TimeNestAppTheme

@Composable
fun AimScreen(item: List<Item>, UpdateFuncition: (Item) -> Unit, deleteItem: (Item) -> Unit){
 Box(modifier = Modifier
     .fillMaxSize()
     .background(Color.White))
 {
     LazyColumn(
         modifier = Modifier.fillMaxSize(),
         contentPadding = PaddingValues(16.dp),
         verticalArrangement = Arrangement.spacedBy(8.dp)
     ) {
         items(item) { currentItem ->

             GoalsROW(currentItem, UpdateFuncition,deleteItem)


         }
     }
 }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalsROW(item: Item, UpdateFuncition: (Item) -> Unit, deleteItem: (Item) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(GolasRowColor)
            .height(80.dp)
            .combinedClickable(
                onClick = {},
                onLongClick = {

                    item.State=0
                    UpdateFuncition(item)
                }
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.missionName ?: "",
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 8.dp, top = 8.dp),
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )

            Image(
                painter = painterResource(com.muhammedturgut.timenestapp.R.drawable.rowdelete),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        deleteItem(item)
                    }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Başlama Tarihi: ${item.startTime}",
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 8.dp, top = 8.dp),
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                color = GolasRowColorText,
                fontWeight = FontWeight.Normal
            )

            Text(
                text = "Bitiş Tarihi: ${item.endTime}",
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 8.dp, top = 8.dp),
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                color = GolasRowColorText,
                fontWeight = FontWeight.Normal
            )

            Image(
                painter = painterResource(com.muhammedturgut.timenestapp.R.drawable.rowedit),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        // item düzenleme bildirimi
                    }
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