package com.bedrock.encryptednotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bedrock.encryptednotes.data.MemoDao
import com.bedrock.encryptednotes.data.MemoModel
import com.bedrock.encryptednotes.data.MemoRepository
import com.bedrock.encryptednotes.misc.Encryption
import com.bedrock.encryptednotes.viewmodel.MemoViewModel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MemoViewModelTest {
    @JvmField
    @Rule
    val rule = InstantTaskExecutorRule()

    private val spyRepository = MockRepository(MockMemoDao())
    private val viewModel = MemoViewModel(spyRepository, Encryption())

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.bedrock.encryptednotes", appContext.packageName)
    }

    @Test
    fun insertMemo() = runBlocking {
        viewModel.insertMemo("Title 5", "Subtitle 5", "Memo 5").join()
        val lastMemo = spyRepository.allMemos.value?.get(8)
        assertEquals(lastMemo?.title, "Title 5")
    }

    @Test
    fun deleteMemo() = runBlocking {
        val sizeBeforeDeletion = spyRepository.allMemos.value?.size
        viewModel.deleteMemo(6).join()
        val sizeAfterDeletion = spyRepository.allMemos.value?.size
        val deletedItemIfExists = spyRepository.allMemos.value?.find { it.id == 6 }
        assertEquals(sizeBeforeDeletion?.minus(1), sizeAfterDeletion)
        assertEquals(deletedItemIfExists, null)
    }

    @Test
    fun addFromJson() = runBlocking {
        val sizeBeforeDeletion = spyRepository.allMemos.value?.size
        val success = viewModel.addFromJson(testImportstring).await()
        val sizeAfterDeletion = spyRepository.allMemos.value?.size
        val addedItem1 = spyRepository.allMemos.value?.get(sizeAfterDeletion?.minus(1) ?: 0)
        val addedItem2 = spyRepository.allMemos.value?.get(sizeAfterDeletion?.minus(2) ?: 0)
        assertEquals(sizeBeforeDeletion?.plus(2), sizeAfterDeletion)
        assertEquals(addedItem2?.title, "bank")
        assertEquals(addedItem1?.title, "Mobile phone")
        assert(success)
    }

    @Test
    fun addFromJsonEmptyString() = runBlocking {
        val sizeBeforeDeletion = spyRepository.allMemos.value?.size
        val sucess = viewModel.addFromJson("").await()
        val sizeAfterDeletion = spyRepository.allMemos.value?.size
        assertEquals(sizeBeforeDeletion, sizeAfterDeletion)
        assert(!sucess)
    }



    @Test
    fun filterItems() = runTest {
        val observer = Observer<List<MemoModel>> {}
        try {
            viewModel.items.observeForever(observer)
            viewModel.filterItems("netflix")
            val filteredItems = viewModel.items.value
            assertEquals(1, filteredItems?.size)
            assertEquals("Netflix", filteredItems?.get(0)?.title)

            viewModel.filterItems("")
            val filteredItems2 = viewModel.items.value
            assertEquals(8, filteredItems2?.size)
        } finally {
            viewModel.items.removeObserver(observer)
        }
    }

    @Test
    fun getAllItemsJson() = runTest {
        val observer = Observer<List<MemoModel>> {}
        try {
            viewModel.items.observeForever(observer)

            val text = viewModel.getAllItemsJson()
            assert(text.contains("Amazon"))
            assert(text.contains("Gmail"))
            assert(text.contains("Netflix"))
            assert(text.contains("Bank"))
            assert(text.contains("Title 1"))
            assert(text.contains("Title 2"))
            assert(text.contains("Title 3"))
            assert(text.contains("Title 4"))
            assert(text.contains("Subtitle 1"))
            assert(text.contains("Subtitle 1"))
            assert(text.contains("Subtitle 1"))
            assert(text.contains("Subtitle 1"))
            assert(text.contains("user name 4"))
            assert(text.contains("info"))
            assert(text.contains("info2"))
        } finally {
            viewModel.items.removeObserver(observer)
        }
    }
}

class MockRepository(memoDao: MemoDao) : MemoRepository(memoDao) {

}

class MockMemoDao : MemoDao {

    val fakeMemoDB = mutableListOf(
        MemoModel(1, "Title 1", "Subtitle 1", "aaaa"),
        MemoModel(2, "Title 2", "Subtitle 2", "aaaa"),
        MemoModel(3, "Title 3", "Subtitle 3", "aaaa"),
        MemoModel(4, "Title 4", "", "aaaa"),
        MemoModel(5, "Amazon", "user name 4", "aaaa"),
        MemoModel(6, "Gmail", "username  4", "aaaa"),
        MemoModel(7, "Netflix", "info", "Memo 4"),
        MemoModel(8, "Bank", "info2", "Memo 4"),
    )

    override fun getAllMemos(): LiveData<List<MemoModel>> {
        val liveData = MutableLiveData<List<MemoModel>>()
        liveData.postValue(fakeMemoDB)
        return liveData
    }

    override suspend fun insertMemo(memo: MemoModel) {
        fakeMemoDB.add(memo)
    }

    override suspend fun deleteMemo(memo: MemoModel) {
        fakeMemoDB.remove(memo)
    }

    override suspend fun insertAllMemos(memos: List<MemoModel>) {
        fakeMemoDB.addAll(memos)
    }

    override fun searchMemos(query: String): LiveData<List<MemoModel>> {
        //no-op
        val liveData = MutableLiveData<List<MemoModel>>()
        liveData.postValue(fakeMemoDB)
        return liveData
    }
}

val testImportstring =
    "{\"accounts\":[{\"title\": \"bank\",\"subTitle\": \"username\",\"memo\": \"firstpassword!\"},{\"title\": \"Mobile phone\",\"subTitle\": \"email@gmail.com\",\"memo\": \"somepassword\"}]}"
