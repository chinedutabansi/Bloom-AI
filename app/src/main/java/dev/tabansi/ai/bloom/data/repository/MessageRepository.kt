package dev.tabansi.ai.bloom.data.repository

import dev.tabansi.ai.bloom.data.entity.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun insertMessage(message: Message)
    fun selectMessage(id: Int): Flow<Message>
    fun selectMessagesByChat(chatId: Int): Flow<List<Message>>
}