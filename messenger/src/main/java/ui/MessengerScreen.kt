package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import viewmodel.MessageScreenViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.messenger.R
import com.example.networking.Resource
import repo.Message
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavController
import com.example.infra.FabState
import com.example.infra.TopBarState

@Composable
fun MessengerScreen(
    navController: NavController,
    viewModel: MessageScreenViewModel,
    setFabState: (FabState) -> Unit,
    setTopBarState: (TopBarState) -> Unit
) {
    // Have a list of messages i want to display.
    // I'll need a VM to handle the stateflow
    // Use lazycolumn to display messages

    /*
        Using val messages = ... gives you the State<T> object, so you access the value with messages.value.
        Using val messages by ... uses Kotlin's delegation, so you access the value directly as messages.
        Summary:
        Use by for direct value access. Need to import getValue
        Use = if you need the full State<T> object (for .value or passing as a parameter).
     */

    var scrollState = rememberLazyListState()
    val messages by viewModel.messages.collectAsState()
    var lastAnimatedId by remember { mutableIntStateOf(
        (messages as Resource.Success<List<Message>>).data.size
    ) }

    LaunchedEffect(Unit) {
        setTopBarState(
            TopBarState(
                isVisible = true,
                title = "Messages",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = { navController.navigateUp() }
            )
        )
    }

    LaunchedEffect(Unit) {
        setFabState(FabState(
            isVisible = true,
            icon = Icons.Filled.Add,
            contentDescription = "Add new message",
            onClick = {
                viewModel.addNewMessage()
            }
        ))
    }

    LaunchedEffect(messages) {
        if (messages is Resource.Success) {
            scrollState.animateScrollToItem((messages as Resource.Success<List<Message>>)
                .data[(messages as Resource.Success<List<Message>>).data.size - 1]
                .id)
        }
    }

    when (messages) {
        is Resource.Error -> {
            val error = messages as Resource.Error
            Text("Error: ${error.message}")
        }
        is Resource.Loading -> {
            CircularProgressIndicator()
        }
        is Resource.Success-> {
            LazyColumn(
                state = scrollState,
                reverseLayout = true
            ) {
                items((messages as Resource.Success<List<Message>>).data, key = { it.id }) { message ->
                    MessagePreview(
                        modifier = Modifier,
                        message = message
                    )
                }
            }
        }
    }
}

@Composable
fun AnimatedMessagePreview(
    message: Message
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(message.id) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(animationSpec = tween(3000)) + scaleIn(),
        exit = fadeOut()
    ) {
        MessagePreview(message = message)
    }
}

@Composable
fun MessagePreview(
    modifier: Modifier = Modifier,
    message: Message
) {
        Row(modifier = modifier.padding(8.dp).semantics(mergeDescendants = true) {
            contentDescription = "Message from ${message.sender}: ${message.content}."
        }) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Profile image",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(64.dp)
                    .border(2.dp, MaterialTheme.colorScheme.outline ,CircleShape)
            )

            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                Row {
                    Text(
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold,
                        text = message.sender
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = message.timestamp.toString())
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = ""
                    )
                }

                Text(
                    modifier = Modifier,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = message.content
                )
            }

        }
}

@Preview
@Composable
fun MessageComposePreview() {
    MessagePreview(message = Message(
        id = 1,
        sender = "Alex",
        content = "Get a job you loser",
        timestamp = 100
    ))
}