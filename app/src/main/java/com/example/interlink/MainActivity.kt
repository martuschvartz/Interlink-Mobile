package com.example.interlink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.interlink.ui.InterlinkApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InterlinkApp()
        }
    }
}

@Composable
fun MainActivityPreview(){
    InterlinkApp()
}



