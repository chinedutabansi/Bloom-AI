package dev.tabansi.ai.bloom.data.repository

import dev.tabansi.ai.bloom.data.dao.MessageDao
import dev.tabansi.ai.bloom.data.entity.Message
import kotlinx.coroutines.flow.Flow

class OfflineMessageRepository(private val messageDao: MessageDao) : MessageRepository {
    override suspend fun insertMessage(message: Message) = messageDao.insert(message)
    override fun selectMessage(id: Int): Flow<Message> = messageDao.select(id)
    override fun selectMessagesByChat(chatId: Int): Flow<List<Message>> =
        messageDao.selectAllByChat(chatId)
}