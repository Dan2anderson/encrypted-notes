package com.example.encryptednotes

import android.app.Application
import com.example.encryptednotes.data.MemoDatabase
import com.example.encryptednotes.data.MemoRepository

class MemoApplication : Application() {
    val database by lazy { MemoDatabase.getDatabase(this) }
    val repository by lazy { MemoRepository(database.memoDao()) }
}
