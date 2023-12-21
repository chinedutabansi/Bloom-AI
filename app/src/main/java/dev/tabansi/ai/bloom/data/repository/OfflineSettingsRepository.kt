package dev.tabansi.ai.bloom.data.repository

import dev.tabansi.ai.bloom.data.dao.SettingsDao
import dev.tabansi.ai.bloom.data.entity.Settings
import kotlinx.coroutines.flow.Flow

class OfflineSettingsRepository(private val settingsDao: SettingsDao) : SettingsRepository {
    override suspend fun insertSettings(settings: Settings) = settingsDao.insert(settings)
    override suspend fun updateSettings(settings: Settings) = settingsDao.update(settings)
    override fun selectSettingsByChat(chatId: Int): Flow<Settings> = settingsDao.select(chatId)
}