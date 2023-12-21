package dev.tabansi.ai.bloom.data.repository

import dev.tabansi.ai.bloom.data.dao.ChatDao
import dev.tabansi.ai.bloom.data.entity.Chat
import kotlinx.coroutines.flow.Flow

class OfflineChatRepository(private val chatDao: ChatDao) : ChatRepository {
    override suspend fun insertChat(chat: Chat) =chatDao.insert(chat)
    override suspend fun updateChat(chat: Chat) = chatDao.update(chat)
    override suspend fun deleteChat(chat: Chat) = chatDao.delete(chat)
    override fun selectChat(id: Int): Flow<Chat> = chatDao.select(id)
    override fun selectAllChats(): Flow<List<Chat>> = chatDao.selectAll()
}