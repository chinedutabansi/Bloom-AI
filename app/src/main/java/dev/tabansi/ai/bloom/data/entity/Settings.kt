package dev.tabansi.ai.bloom.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "settings",
    foreignKeys = [
        ForeignKey(
            entity = Chat::class,
            parentColumns = ["id"],
            childColumns = ["chat_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Settings(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "chat_id")
    val chatId: Int,
    @ColumnInfo(name = "max_tokens")
    val maxTokens: Int = 4096, // max output tokens, 1 token = ~4 chars
    @ColumnInfo(name = "temperature")
    val temperature: Float = 0.25f, // degree of randomness: 0.0 = greedy, 1.0 = random
    @ColumnInfo(name = "top_k")
    val topK: Int = 40,
    @ColumnInfo(name = "top_p")
    val topP: Int = 0,
    @ColumnInfo(name = "candidate_count")
    val candidateCount: Int = 1
)
