package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networking.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import repo.Message
import repo.MessageRepo
import javax.inject.Inject

class MessageScreenViewModel @Inject constructor(
    val repo: MessageRepo
) : ViewModel() {

    private val _messages = MutableStateFlow<Resource<List<Message>>>(Resource.Loading())
    val messages: StateFlow<Resource<List<Message>>> = _messages

    /*
        Coroutines are lightweight, cooperative tasks for asynchronous programming in Kotlin. Here’s a breakdown:
        Not threads: Coroutines run on threads but are much lighter. You can have thousands of coroutines on a few threads.
        Suspend functions: Marked with suspend, these can pause and resume without blocking the thread.
        Dispatchers: Control which thread or thread pool the coroutine runs on (Dispatchers.Main, Dispatchers.IO, etc.).
        Scope: Defines the lifecycle of coroutines (viewModelScope, GlobalScope, etc.).
        Launch vs async: launch starts a coroutine that doesn’t return a result; async returns a
        Deferred for a result.
        Structured concurrency: Coroutines are tied to a scope, so they’re automatically cancelled
        when the scope is destroyed (e.g., when a ViewModel is cleared).
     *
     */

    init {
        viewModelScope.launch {
            _messages.value = Resource.Loading()
            _messages.value = repo.getMessages()
        }
    }

    fun addNewMessage() {
        viewModelScope.launch {
            val currentMessages = _messages.value

            if (currentMessages is Resource.Success) {
                val updatedList = currentMessages.data.toMutableList()
                val newId = (updatedList.maxOfOrNull { it.id } ?: 0) + 1
                updatedList.add(Message(
                    id = newId,
                    sender = "Potential Scam",
                    content = "This is the most recent message.",
                    timestamp = 5
                ))
                _messages.value = Resource.Success(updatedList)
            }
        }
    }
}
