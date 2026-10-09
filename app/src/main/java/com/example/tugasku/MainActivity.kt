package com.example.tugasku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tugasku.ui.navigation.AppNavGraph
import com.example.tugasku.ui.theme.TugasKuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TugasKuTheme {
                AppNavGraph()
            }
        }
    }
}