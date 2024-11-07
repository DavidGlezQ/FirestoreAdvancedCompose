package com.david.glez.firestoreadvancedcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.david.glez.firestoreadvancedcompose.ui.home.HomeScreen
import com.david.glez.firestoreadvancedcompose.ui.home.HomeViewModel
import com.david.glez.firestoreadvancedcompose.ui.theme.FirestoreAdvancedComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirestoreAdvancedComposeTheme {
                val homeViewModel by viewModels<HomeViewModel>()
                HomeScreen(homeViewModel)
            }
        }
    }
}