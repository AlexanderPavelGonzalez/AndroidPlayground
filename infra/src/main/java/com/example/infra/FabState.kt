package com.example.infra

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector

data class FabState(
    val isVisible: Boolean,
    val icon: ImageVector,
    val contentDescription: String,
    val onClick: () -> Unit
)

val defaultFabState = FabState(false, Icons.Filled.Add, "", {})