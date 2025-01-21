package com.ritesh.movietvapp.activities

import HomeViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ritesh.movietvapp.component.AppNavigation
import com.ritesh.movietvapp.ui.theme.MovieTVAppTheme
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieTVAppTheme {
                val homeViewModel: HomeViewModel = getViewModel()
                AppNavigation(homeViewModel)
            }
        }
    }
}