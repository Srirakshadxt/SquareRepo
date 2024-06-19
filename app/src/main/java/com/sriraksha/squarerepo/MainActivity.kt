package com.sriraksha.squarerepo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sriraksha.squarerepo.presentation.SquareRepoViewModel
import com.sriraksha.squarerepo.ui.screens.SquareRepoScreen
import com.sriraksha.squarerepo.ui.theme.SquareRepoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SquareRepoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        viewModel.loadSquareRepositories()
        setContent {
            SquareRepoTheme {
                SquareRepoScreen(viewModel)
            }
        }
    }
}