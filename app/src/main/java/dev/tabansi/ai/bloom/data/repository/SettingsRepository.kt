package dev.tabansi.ai.bloom.data.repository

import dev.tabansi.ai.bloom.data.entity.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun insertSettings(settings: Settings)
    suspend fun updateSettings(settings: Settings)
    fun selectSettingsByChat(chatId: Int): Flow<Settings>
}