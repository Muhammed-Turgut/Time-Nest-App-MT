package com.muhammedturgut.timenestapp.ChainBreakingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemChain
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemDetailChain
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ViewModel.ChainViewModel
import com.muhammedturgut.timenestapp.R

@Composable
fun DetailsScreenChainBreaking(navHostController: NavHostController,
                               viewModel: ChainViewModel,
                               notId:Int?,
                               itemsDetails: List<ItemDetailChain>) {

    //Burda uygun olan item aldık.
    val item = remember { mutableStateOf<ItemChain?>(null) }
    LaunchedEffect(notId) {
        item.value = viewModel.getItemNot(notId ?: 1)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(top = 16.dp)) {

                IconButton(
                    onClick = { navHostController.navigate("ChainBreaking") },
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.backbutton),
                        contentDescription = "Back"
                    )
                }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.chainbreakingicon),
                    contentDescription = null,
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${item.value?.chainName}",
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
                    text = "${item.value?.chainAbout}",
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
                Row(modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {

                    Image(painter = painterResource(R.drawable.detailsaddbutton), contentDescription = null)
                }


                LazyColumn {
                   items(itemsDetails){ item ->
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
fun DetailsRowDay(item:ItemDetailChain){
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)){
        Row {
            Text(text="${item.notAbout}",
                fontSize = 13.3.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f))

        }
    Text(text="merhaba", fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
   }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewDetails() {
    val navHostController = rememberNavController()

}
