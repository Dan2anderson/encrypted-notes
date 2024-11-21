package com.example.encryptednotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.encryptednotes.data.MemoDao
import com.example.encryptednotes.data.MemoModel
import com.example.encryptednotes.data.MemoRepository
import com.example.encryptednotes.misc.Encryption
import com.example.encryptednotes.viewmodel.MemoViewModel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.json.JSONObject

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
//@RunWith(MockitoJUnitRunner::class)
class ExampleInstrumentedTest {
    @JvmField
    @Rule
    val rule = InstantTaskExecutorRule()

    val spyRepository = MockRepository(MockMemoDao())
    val viewModel = MemoViewModel(spyRepository, Encryption())

    lateinit var vm2: MemoViewModel
    lateinit var mockRepository: MemoRepository
    lateinit var mockEncryption: Encryption

    @Before
    fun setUp() {//todo we need to mock using an alternative to mockito
//        mockRepository = Mockito.mock(MemoRepository::class.java)
        mockRepository = mock(MemoRepository::class.java)
        mockEncryption = mock(Encryption::class.java)
        vm2 = MemoViewModel(mockRepository, mockEncryption)

        val memos = listOf(
            MemoModel(1, "Title 1", "Subtitle 1", "aaaa"),
            MemoModel(2, "Title 2", "Subtitle 2", "aaaa"),
            MemoModel(3, "Netflix", "Subtitle 3", "aaaa"),
            MemoModel(4, "Title 4", "", "aaaa")
        )

        Mockito.`when`(mockRepository.allMemos).thenReturn(MutableLiveData(memos))
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.encryptednotes", appContext.packageName)
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
        viewModel.addFromJson(testImportstring).join()
        val sizeAfterDeletion = spyRepository.allMemos.value?.size
        val addedItem1 = spyRepository.allMemos.value?.get(sizeAfterDeletion?.minus(1) ?: 0)
        val addedItem2 = spyRepository.allMemos.value?.get(sizeAfterDeletion?.minus(2) ?: 0)
        assertEquals(sizeBeforeDeletion?.plus(2), sizeAfterDeletion)
        assertEquals(addedItem2?.title, "bank")
        assertEquals(addedItem1?.title, "Mobile phone")
    }



    @Test
    fun filterItems() = runTest {
        val observer = Mockito.mock(Observer::class.java) as Observer<List<MemoModel>>
        vm2.items.observeForever(observer)

        vm2.filterItems("netflix")

        val filteredItems = vm2.items.value
        assertEquals(1, filteredItems?.size)
        assertEquals("Netflix", filteredItems?.get(0)?.title)

        vm2.items.removeObserver(observer)
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
val testImportJson: JSONObject =
    JSONObject(testImportstring)