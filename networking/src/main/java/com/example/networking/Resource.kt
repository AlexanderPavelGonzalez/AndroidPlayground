package com.example.networking

/**
 * The <T> means I am using the Resource class to wrap a generic type.
 *
 * Sealed classes are similar to abstract classes in that they cannot
 * be instantiated directly. You can only create instances of their subclasses.
 * Sealed classes are used to restrict which classes can inherit from them, providing
 * more control and safety in your code.
 */
sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
    class Loading<T> : Resource<T>()
}