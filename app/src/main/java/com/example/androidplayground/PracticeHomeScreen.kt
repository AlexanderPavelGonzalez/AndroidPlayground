package com.example.androidplayground

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.androidplayground.models.PracticeScreen
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.infra.FabState
import com.example.infra.TopBarState
import com.example.infra.defaultFabState
import com.example.infra.defaultTopBarState

@Composable
fun PracticeHomeScreen(
    navController: NavController,
    setFabState: (FabState) -> Unit,
    setTopBarState: (TopBarState) -> Unit
) {
    val items = listOf(
        PracticeScreen.MessengerExample
    )

    LaunchedEffect(Unit) {
        setFabState(defaultFabState)
    }

    LaunchedEffect(Unit) {
        setTopBarState(defaultTopBarState)
    }

    LazyColumn {
        items(items) { screen ->
            HorizontalDivider()
            Text(
                text = screen.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate(screen.route) }
                    .padding(12.dp)
            )
            HorizontalDivider()
        }
    }

}