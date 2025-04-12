package com.muhammedturgut.timenestapp.ChainBreakingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammedturgut.timenestapp.R

@Composable
fun DetailsScreenChainBreaking() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 16.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.backbutton),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.chainbreakingicon),
                    contentDescription = null,
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Chain Name",
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.righteousregular))
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top=16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.backbutton),
                        contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                            .align(alignment = Alignment.Bottom)
                    )
                    Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "2025", fontSize = 14.sp, fontWeight = FontWeight.Normal)
                        Text(text = "Mart", fontSize = 16.sp,fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 16.dp,end=16.dp))
                    }


                    Image(
                        painter = painterResource(R.drawable.nextbutton),
                        contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                            .align(alignment = Alignment.Bottom)
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth()){

                LazyColumn {
                   items(detailsListItem){ item ->
                       DetailsRowDay(item)

                   }
                }

            }


        }
    }
}

@Composable
fun detailsDays(){

}

@Composable
fun DetailsRowDay(item:detailsList){
    Column {
        Row {
            Text(text="04 Mart 2025", fontSize = 13.3.sp, fontWeight = FontWeight.SemiBold)
            Image(painter = painterResource(R.drawable.detailsaddbutton), contentDescription = null)
        }
    Text(text="merhaba", fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
   }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewDetails() {
    DetailsScreenChainBreaking()
}

val detailsListItem= listOf(
    detailsList("Kitap Oku"," Text(text=\"04 Mart 2025\", fontSize = 13.3.sp, fontWeight = FontWeight.SemiBold)","Bu notum"))

class detailsList(name:String,about:String,note:String){}