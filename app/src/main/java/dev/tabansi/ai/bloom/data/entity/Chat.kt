package dev.tabansi.ai.bloom.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class Chat(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "title_set")
    val titleSet: Boolean = false,
    @ColumnInfo(name = "last_update")
    val lastUpdate: Long,
)
