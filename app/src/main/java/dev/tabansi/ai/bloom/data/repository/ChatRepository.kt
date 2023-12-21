package dev.tabansi.ai.bloom.data.repository

import dev.tabansi.ai.bloom.data.entity.Chat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun insertChat(chat: Chat)
    suspend fun updateChat(chat: Chat)
    suspend fun deleteChat(chat: Chat)
    fun selectChat(id: Int): Flow<Chat>
    fun selectAllChats(): Flow<List<Chat>>
}