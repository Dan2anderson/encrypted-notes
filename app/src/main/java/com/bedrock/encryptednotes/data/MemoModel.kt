package com.bedrock.encryptednotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memos_table")
data class MemoModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val subTitle: String,
    val memo: String
)