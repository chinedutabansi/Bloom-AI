package dev.tabansi.ai.bloom.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tabansi.ai.bloom.data.repository.ChatRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeViewModel(private val chatRepository: ChatRepository) : ViewModel() {
    val uiState: StateFlow<HomeUiState> =
        chatRepository.selectAllChats().map {
            HomeUiState(
                chats = it.map { chat ->
                    ChatInfo(
                        id = chat.id,
                        title = chat.title,
                        lastUpdate = formatUnixTime(chat.lastUpdate)
                    )
                }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(chats = emptyList())
        )

    // Todo might move this to the screen or navigation
    private fun addChat() {

    }

    // Todo add confirmation before calling this function, then remove visibility modifier
    private fun deleteChat(chatId: Int) {
        viewModelScope.launch {
            chatRepository.deleteChat(
                chatRepository.selectChat(chatId).single()
            )
        }
    }

    private fun formatUnixTime(unixTime: Long): String{
        val simpleDateFormat: SimpleDateFormat
        val calendar = Calendar.getInstance()
        val currentTime = calendar.time.time / 1000
        val difference = currentTime - unixTime

        return when {
            difference < 24 * 3600 -> { // less than 24 hours
                simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
                simpleDateFormat.format(Date(unixTime * 1000L))
            }
            difference < 48 * 3600 -> { // between 24 and 48 hours
                "Yesterday"
            }
            difference < 7 * 24 * 3600 -> { // less than 7 days
                simpleDateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
                simpleDateFormat.format(Date(unixTime * 1000L))
            }
            difference < 365 * 24 * 3600 -> { // less than 1 year
                simpleDateFormat = SimpleDateFormat("MMM d", Locale.getDefault())
                simpleDateFormat.format(Date(unixTime * 1000L))
            }
            else -> { // more than 1 year
                simpleDateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
                simpleDateFormat.format(Date(unixTime * 1000L))
            }
        }
    }
}

data class HomeUiState(
    val chats: List<ChatInfo>
)

data class ChatInfo(
    val id: Int,
    val title: String,
    val lastUpdate: String
)