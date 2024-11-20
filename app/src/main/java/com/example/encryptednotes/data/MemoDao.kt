package com.example.encryptednotes.data

import androidx.lifecycle.LiveData

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface MemoDao {
    @Query("SELECT * FROM memos_table ORDER BY title ASC")
    fun getAllMemos(): LiveData<List<MemoModel>>

    @Insert
    suspend fun insertMemo(memo: MemoModel)

    @Delete
    suspend fun deleteMemo(memo: MemoModel)

    @Insert
    suspend fun insertAllMemos(memos: List<MemoModel>)

    @Query("SELECT * FROM memos_table WHERE title LIKE :query OR subTitle LIKE :query")
    fun searchMemos(query: String): LiveData<List<MemoModel>>
}