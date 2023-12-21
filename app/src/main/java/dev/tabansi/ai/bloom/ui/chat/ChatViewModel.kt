package dev.tabansi.ai.bloom.ui.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tabansi.ai.bloom.data.repository.ChatRepository
import dev.tabansi.ai.bloom.data.repository.MessageRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.stateIn

/* TODO decide on Single or multiple repositories
*   if so, convert multiple repositories into one
* */
class ChatViewModel(
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val chatId: Int = checkNotNull(savedStateHandle[ChatDestination.chatIdArg])
    val uiState: StateFlow<ChatUiState> = chatRepository.selectChat(chatId)
        .map {
            ChatUiState(
                title = it.title,
                titleSet = it.titleSet,
                messages = messageRepository.selectMessagesByChat(chatId).map { messageList ->
                    messageList.map { message ->
                        MessageInfo(
                            sender = message.sender,
                            content = message.content
                        )
                    }
                }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf()).value
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ChatUiState())

    suspend fun updateTitle(title: String) {
        val chat = chatRepository.selectChat(chatId)
        chatRepository.updateChat(
            chat.single().copy(
                title = title,
                titleSet = true
            )
        )
    }
}

data class ChatUiState(
    val title: String = "",
    val titleSet: Boolean = false,
    val messages: List<MessageInfo> = listOf(),
    val inputBlocked: Boolean = false,
    val currentUserInput: String = ""
)

data class MessageInfo(
    val sender: String,
    val content: String
)