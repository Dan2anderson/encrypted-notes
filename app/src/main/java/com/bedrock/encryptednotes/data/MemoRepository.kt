package com.bedrock.encryptednotes.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

open class MemoRepository(private val memoDao: MemoDao) {

    val allMemos: LiveData<List<MemoModel>> = memoDao.getAllMemos()

    fun searchMemos(query: String): LiveData<List<MemoModel>> {
        return memoDao.searchMemos("%$query%")
    }
    suspend fun delete(memo: MemoModel) {
        memoDao.deleteMemo(memo)
    }

    suspend fun insertAll(memos: List<MemoModel>) {
        memoDao.insertAllMemos(memos)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(memo: MemoModel) {
        memoDao.insertMemo(memo)
    }
}