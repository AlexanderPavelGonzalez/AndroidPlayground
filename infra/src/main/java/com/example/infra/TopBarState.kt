package com.example.infra

import androidx.compose.ui.graphics.vector.ImageVector

data class TopBarState(
    val isVisible: Boolean,
    val title: String,
    val icon: ImageVector?,
    val onClick: () -> Unit
)

val defaultTopBarState = TopBarState(
    isVisible = true,
    title = "Gonzo's Android Playground",
    icon = null,
    onClick = {}
)