package com.chomp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.chomp.feature.homeList.HomeListScreen
import com.chomp.feature.login.LoginScreen
import com.chomp.navigation.ChompNavHost
import com.chomp.ui.theme.ChompTheme
import org.koin.androidx.compose.KoinAndroidContext

/**
 * Improvement:
 * all packages can be exported to separate
 * feature/library modules
 * */
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChompTheme {
                Scaffold(Modifier.fillMaxSize()) {
                    ChompNavHost(
                        navController = rememberNavController()
                    )
                }
            }
        }
    }
}