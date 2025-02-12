package com.muhammedturgut.timenestapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import com.muhammedturgut.timenestapp.ui.theme.PrimaryColor
import com.muhammedturgut.timenestapp.ui.theme.TimeNestAppTheme

class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeNestAppTheme {
              SplashScreenLogo()
            }
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        TransitionIntent()
    }

    private fun TransitionIntent(){
        Handler().postDelayed({
            val intent= Intent(this@SplashScreen,MainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}

@Composable
fun SplashScreenLogo(){
Box(modifier = Modifier.fillMaxSize()
    .background(PrimaryColor)){

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.timenest),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(140.dp, 90.dp))
    }


}
}


@Preview(showBackground = true)
@Composable
fun SplashScreenLogoPreview() {
    TimeNestAppTheme {
        SplashScreenLogo()
    }
}