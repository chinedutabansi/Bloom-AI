package dev.tabansi.ai.bloom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.tabansi.ai.bloom.data.entity.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(message: Message)

    @Query("select * from messages where id == :id")
    fun select(id: Int): Flow<Message>

    @Query("select * from messages where chat_id == :chatId order by timestamp asc")
    fun selectAllByChat(chatId: Int): Flow<List<Message>>
}