package dev.tabansi.ai.bloom.ui.chat

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tabansi.ai.bloom.R
import dev.tabansi.ai.bloom.ui.AppViewModelProvider
import dev.tabansi.ai.bloom.ui.BloomDestination

object ChatDestination : BloomDestination {
    override val route: String = "chat"
    const val chatIdArg: String = "chatId"
    val routeWithArgs: String = "$route/{$chatIdArg}"
}

@Composable
fun ChatScreen(
//    navigateToChat: (Int) -> Unit,
//    contentPadding: PaddingValues,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val chatUiState = chatViewModel.uiState.collectAsState().value
    val textFieldHeight = rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = modifier
    ) {
        MessageList(
//            contentPadding = contentPadding,
            messages = chatUiState.messages,
            textFieldHeight = textFieldHeight,
            modifier = Modifier.weight(1f)
        )
        MessageTextField(
            inputBlocked = chatUiState.inputBlocked,
            onSendClick = {  },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
                .onGloballyPositioned { textFieldHeight.intValue = it.size.height }
        )
    }
}

// TODO align bloom chat horizontal end
@Composable
fun MessageList(
//    contentPadding: PaddingValues,
    messages: List<MessageInfo>,
    textFieldHeight: MutableState<Int>,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = modifier,
        state = lazyListState
//        contentPadding = PaddingValues(
//            start = contentPadding.calculateStartPadding(LayoutDirection.Ltr),
//            top = contentPadding.calculateTopPadding(),
//            end = contentPadding.calculateEndPadding(LayoutDirection.Ltr),
//            bottom = with(LocalDensity.current) { textFieldHeight.value.toDp() }
//        )
    ) {
        items(messages) { message ->
            MessageCard(
                sender = message.sender,
                content = message.content,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Spacer(
                modifier = Modifier.height(
                    with(LocalDensity.current) { textFieldHeight.value.toDp() }
                )
            )
        }
    }
    LaunchedEffect(messages) {
        lazyListState.animateScrollToItem(messages.size - 1)
//        coroutineScope.launch {
//            delay(200)
//            lazyListState.animateScrollBy(10000f)
//        }
    }
}

// TODO: background neutral (or maybe gray) for user message, tertiary or accent for bloom message
@Composable
fun MessageCard(
    sender: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            if (sender == "user") MaterialTheme.colorScheme.onSecondary
            else MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = content,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun MessageTextField(
    inputBlocked: Boolean,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var userInput by rememberSaveable { mutableStateOf("") }

    TextField(
        value = userInput,
        onValueChange = { userInput = it },
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 0.dp)
            .imePadding(),
        shape = RoundedCornerShape(16.dp),
        maxLines = 6,
        trailingIcon = { if (!inputBlocked) SendInputButton(onSendClick) else StopInputButton() },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun SendInputButton(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(R.drawable.send_input_icon),
            contentDescription = "Send input",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(2.dp)
        )
    }
}

@Composable
fun StopInputButton() {
    var showToast by rememberSaveable { mutableStateOf(false) }

    IconButton(
        onClick = { showToast = true }
    ) {
        Icon(
            painter = painterResource(R.drawable.block_input_icon),
            contentDescription = "Input is not currently available",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(2.dp)
        )
    }

    if (showToast) {
        ToastError()
        showToast = false
    }
}

@Composable
fun ToastError() {
    val context = LocalContext.current
    val message = "Cannot submit new chat prompt while response is generating"
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}