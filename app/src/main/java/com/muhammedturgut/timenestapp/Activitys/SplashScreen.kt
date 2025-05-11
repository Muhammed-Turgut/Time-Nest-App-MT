package com.muhammedturgut.timenestapp.Activitys

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import app.rive.runtime.kotlin.RiveAnimationView
import com.muhammedturgut.timenestapp.R
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
            val intent= Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        },2500)
    }
}

@Composable
fun SplashScreenLogo(){
Box(modifier = Modifier.fillMaxSize()
    .background(PrimaryColor)){

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        RiveAnimationComposable()
    }


}
}

@Composable
fun RiveAnimationComposable() {
    AndroidView(factory = { context ->
        RiveAnimationView(context).apply {
            setRiveResource(R.raw.timenest)
            play()
        }
    })
}


@Preview(showBackground = true)
@Composable
fun SplashScreenLogoPreview() {
    TimeNestAppTheme {
        SplashScreenLogo()
    }
}