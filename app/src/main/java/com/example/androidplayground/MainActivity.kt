package com.example.androidplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidplayground.models.PracticeScreen
import com.example.androidplayground.ui.theme.GoogleInterviewPrepTheme
import com.example.infra.defaultFabState
import com.example.infra.defaultTopBarState
import ui.MessengerScreen
import viewmodel.MessageScreenViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as TCApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val messageViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MessageScreenViewModel::class.java)
        setContent {
            var fabState by remember { mutableStateOf(defaultFabState) }
            var topBarState by remember {mutableStateOf(defaultTopBarState)}

            GoogleInterviewPrepTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        if (fabState.isVisible) {
                            FloatingActionButton(onClick = fabState.onClick) {
                                Icon(fabState.icon, fabState.contentDescription)
                            }
                        }
                    },
                    topBar = {
                        if (topBarState.isVisible) {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = topBarState.title,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                navigationIcon = {
                                    topBarState.icon?.let { iconButton ->
                                        IconButton(
                                            onClick = topBarState.onClick,
                                        ) {
                                            Icon(iconButton, contentDescription = "Localized description")
                                        }
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                        NavHost(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            startDestination = PracticeScreen.Home.route
                        ) {
                            composable(PracticeScreen.Home.route) {
                                PracticeHomeScreen(
                                    navController = navController,
                                    setFabState = { fabState = it },
                                    setTopBarState = { topBarState = it }
                                )
                            }
                            composable(PracticeScreen.MessengerExample.route) {
                                MessengerScreen(
                                    navController = navController,
                                    viewModel = messageViewModel,
                                    setFabState = { fabState = it },
                                    setTopBarState = { topBarState = it }
                                )
                            }
                        }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GoogleInterviewPrepTheme {
        Greeting("Android")
    }
}