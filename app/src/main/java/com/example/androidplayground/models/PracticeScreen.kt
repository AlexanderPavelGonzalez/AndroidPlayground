package com.example.androidplayground.models

/**
 * sealed class ensures only defined screens exist.
 * Each screen is an object with a route and title.
 * Useful for type-safe navigation and compile-time checks.
 */
sealed class PracticeScreen(val route: String, val title: String) {
    object Home : PracticeScreen("home", "Practice Questions")
    object MessengerExample : PracticeScreen("messenger_example", "Messenger Example")
}