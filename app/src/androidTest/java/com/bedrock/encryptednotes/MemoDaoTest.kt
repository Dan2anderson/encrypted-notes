package com.bedrock.encryptednotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bedrock.encryptednotes.data.MemoDao
import com.bedrock.encryptednotes.data.MemoDatabase
import com.bedrock.encryptednotes.data.MemoModel
import kotlinx.coroutines.runBlocking
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MemoDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MemoDatabase
    private lateinit var memoDao: MemoDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MemoDatabase::class.java
        ).allowMainThreadQueries().build()
        memoDao = database.memoDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertMemoAndGetAllMemos() = runBlocking {
        val memo = MemoModel(1, "Title 1", "Subtitle 1", "Memo 1")
        memoDao.insertMemo(memo)

        val allMemos = memoDao.getAllMemos().getOrAwaitValue()
        assertEquals(1, allMemos.size)
        assertEquals(memo, allMemos[0])
    }

    @Test
    fun deleteMemo() = runBlocking {
        val memo = MemoModel(1, "Title 1", "Subtitle 1", "Memo 1")
        memoDao.insertMemo(memo)
        memoDao.deleteMemo(memo)

        val allMemos = memoDao.getAllMemos().getOrAwaitValue()
        assertEquals(0, allMemos.size)
    }

    @Test
    fun insertAllMemos() = runBlocking {
        val memos = listOf(
            MemoModel(1, "Title 1", "Subtitle 1", "Memo 1"),
            MemoModel(2, "Title 2", "Subtitle 2", "Memo 2")
        )
        memoDao.insertAllMemos(memos)

        val allMemos = memoDao.getAllMemos().getOrAwaitValue()
        assertEquals(2, allMemos.size)
        assertEquals(memos[0], allMemos[0])
        assertEquals(memos[1], allMemos[1])
    }

    @Test
    fun searchMemos() = runBlocking {
        val memos = listOf(
            MemoModel(1, "Netflix", "Subtitle 1", "Memo 1"),
            MemoModel(2, "Amazon", "Subtitle 2", "Memo 2")
        )
        memoDao.insertAllMemos(memos)

        val searchResult = memoDao.searchMemos("Netflix").getOrAwaitValue()
        assertEquals(1, searchResult.size)
        assertEquals(memos[0], searchResult[0])
    }
}

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}