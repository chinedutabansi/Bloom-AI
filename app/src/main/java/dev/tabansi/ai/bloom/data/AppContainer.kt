package dev.tabansi.ai.bloom.data

import android.content.Context
import dev.tabansi.ai.bloom.data.repository.ChatRepository
import dev.tabansi.ai.bloom.data.repository.MessageRepository
import dev.tabansi.ai.bloom.data.repository.OfflineChatRepository
import dev.tabansi.ai.bloom.data.repository.OfflineMessageRepository
import dev.tabansi.ai.bloom.data.repository.OfflineSettingsRepository
import dev.tabansi.ai.bloom.data.repository.SettingsRepository

interface AppContainer {
    val chatRepository: ChatRepository
    val messageRepository: MessageRepository
    val settingsRepository: SettingsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val chatRepository: ChatRepository by lazy {
        OfflineChatRepository(BloomDatabase.getInstance(context).chatDao())
    }
    override val messageRepository: MessageRepository by lazy {
        OfflineMessageRepository(BloomDatabase.getInstance(context).messageDao())
    }
    override val settingsRepository: SettingsRepository by lazy {
        OfflineSettingsRepository(BloomDatabase.getInstance(context).settingsDao())
    }
}
