package com.bedrock.encryptednotes

import android.app.Application
import com.bedrock.encryptednotes.data.MemoDatabase
import com.bedrock.encryptednotes.data.MemoRepository

class MemoApplication : Application() {
    val database by lazy { MemoDatabase.getDatabase(this) }
    val repository by lazy { MemoRepository(database.memoDao()) }
}
