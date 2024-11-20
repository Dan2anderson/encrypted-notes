package com.example.encryptednotes

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.encryptednotes.data.MemoDao
import com.example.encryptednotes.data.MemoModel
import com.example.encryptednotes.data.MemoRepository
import com.example.encryptednotes.misc.Encryption
import com.example.encryptednotes.viewmodel.MemoViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows

//@RunWith(RobolectricTestRunner::class)
@RunWith(MockitoJUnitRunner::class)
class MemoViewModelTest {
//    val mockRepository = MockRepository(MockMemoDao())
//    val viewModel = MemoViewModel(mockRepository, Encryption())


    @Rule
    val rule = InstantTaskExecutorRule()
    @Before
    fun setUp() {
//        val mockLooper = mock(Looper::class.java)
//        Mockito.mockStatic(Looper::class.java).use { mockedStatic ->
//            `when`(Looper.getMainLooper()).thenReturn(mockLooper)
//        }
        Shadows.shadowOf(Looper.getMainLooper()).idle()

    }

    @Test
    fun testMainLooper() {
        val mainLooper = Looper.getMainLooper()
        // Add your test logic here
    }

    @Test
    fun insertMemo() {
//        viewModel.insertMemo("Title 5", "Subtitle 5", "Memo 5")
//        val lastMemo = mockRepository.allMemos.value?.get(8)
//        assertEquals(lastMemo?.title, "Title 5")
    }
}

//class MockRepository(memoDao: MemoDao) : MemoRepository(memoDao) {
//
//}
//
//class MockMemoDao : MemoDao {
//
//    val fakeMemoDB = mutableListOf(
//        MemoModel(1, "Title 1", "Subtitle 1", "aaaa"),
//        MemoModel(2, "Title 2", "Subtitle 2", "aaaa"),
//        MemoModel(3, "Title 3", "Subtitle 3", "aaaa"),
//        MemoModel(4, "Title 4", "", "aaaa"),
//        MemoModel(4, "Amazon", "Subtitle 4", "aaaa"),
//        MemoModel(4, "Gmail", "Subtitle 4", "aaaa"),
//        MemoModel(4, "Netflix", "Subtitle 4", "Memo 4"),
//        MemoModel(4, "Bank", "Subtitle 4", "Memo 4"),
//    )
//
//    override fun getAllMemos(): LiveData<List<MemoModel>> {
//        val liveData = MutableLiveData<List<MemoModel>>()
//        liveData.value = fakeMemoDB
//        return liveData
//    }
//
//    override suspend fun insertMemo(memo: MemoModel) {
//        fakeMemoDB.add(memo)
//    }
//
//    override suspend fun deleteMemo(memo: MemoModel) {
//        fakeMemoDB.remove(memo)
//    }
//
//    override suspend fun insertAllMemos(memos: List<MemoModel>) {
//        fakeMemoDB.addAll(memos)
//    }
//
//    override fun searchMemos(query: String): LiveData<List<MemoModel>> {
//        //no-op
//        val liveData = MutableLiveData<List<MemoModel>>()
//        liveData.value = fakeMemoDB
//        return liveData
//    }
//}