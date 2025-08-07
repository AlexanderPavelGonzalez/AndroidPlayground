package repo

import com.example.networking.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MessageRepo {

    /**
     * Yes, use suspend for any function that might block the main thread, such as network calls,
     * database operations, or heavy computations. This allows the function to run asynchronously
     * without freezing the UI, following best practices in Kotlin and Android development.
     *
     * suspend is used for functions that perform long-running or blocking operations (like network
     * or database calls) so they don’t freeze the UI.
     * withContext(Dispatchers.IO) moves the work to a background thread optimized for IO tasks,
     * keeping the main thread free for UI updates. This is best practice in Android/Kotlin to
     * ensure smooth, responsive apps.
     *
     */
    suspend fun getMessages(): Resource<List<Message>> {
        return withContext(Dispatchers.IO) {
            delay(1000L) // Simulate network delay
             Resource.Success(listOf(
                Message(id = 1, sender = "Josh", content = "Hello", timestamp = 1627842000000),
                Message(id = 2, sender = "Dad", content = "Really really really really" +
                        "really really really really really long message", timestamp = 1627842000000),
                Message(id = 3, sender = "Mom", content = "whats good", timestamp = 1627842000000),
                 Message(id = 4, sender = "Gabriella", content = "Really really really really" +
                         "really really really really really really really really really really really long message",
                     timestamp = 1627842000000),
            ))
        }
    }
}