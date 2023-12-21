package dev.tabansi.ai.bloom.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.tabansi.ai.bloom.data.dao.ChatDao
import dev.tabansi.ai.bloom.data.dao.SettingsDao
import dev.tabansi.ai.bloom.data.dao.MessageDao
import dev.tabansi.ai.bloom.data.entity.Chat
import dev.tabansi.ai.bloom.data.entity.Settings
import dev.tabansi.ai.bloom.data.entity.Message

@Database(
    entities = [Chat::class, Message::class, Settings::class],
    version = 1,
    exportSchema = false
)
abstract class BloomDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        @Volatile
        private var Instance: BloomDatabase? = null
        fun getInstance(context: Context): BloomDatabase =
            Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BloomDatabase::class.java, "bloom_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
    }
}