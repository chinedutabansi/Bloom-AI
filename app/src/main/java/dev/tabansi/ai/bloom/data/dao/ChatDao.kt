package dev.tabansi.ai.bloom.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.tabansi.ai.bloom.data.entity.Chat
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(chat: Chat)

    @Update
    suspend fun update(chat: Chat)

    @Delete
    suspend fun delete(chat: Chat)

    @Query("select * from chats where id == :id")
    fun select(id: Int): Flow<Chat>

    @Query("select * from chats order by last_update desc")
    fun selectAll(): Flow<List<Chat>>
}