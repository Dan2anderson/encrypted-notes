package com.example.encryptednotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.encryptednotes.data.MemoRepository
import com.example.encryptednotes.data.MemoModel
import com.example.encryptednotes.misc.Encryption
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject

class MemoViewModel(private val repository: MemoRepository, private val encryption: Encryption) :
    ViewModel() {

    private val itemFilter = MutableLiveData<String>()
    private val allMemos = repository.allMemos
    private val _items = MediatorLiveData<List<MemoModel>>()
    val items: LiveData<List<MemoModel>> get() = _items

    init {
        _items.addSource(allMemos) { filterMemos() }
        _items.addSource(itemFilter) { filterMemos() }
    }

    private fun filterMemos() {
        val query = itemFilter.value ?: ""
        val memos = allMemos.value ?: emptyList()
        _items.value = memos.filter {
            it.title.contains(
                query,
                ignoreCase = true
            ) || it.subTitle.contains(query, ignoreCase = true)
        }
    }

    fun insertMemo(title: String, subTitle: String, memo: String): Job {
       return viewModelScope.launch {
            val encryptedMemo = encryption.encrypt(memo)
            repository.insert(MemoModel(0, title, subTitle, encryptedMemo))
        }
    }

    fun getMemoPlainText(item: MemoModel): String {
        return encryption.decrypt(item.memo)
    }

    fun deleteMemo(memoId: Int): Job {
        return viewModelScope.launch {
            allMemos.value?.find { it.id == memoId }?.let {
                repository.delete(it)
                filterMemos()
            }
        }
    }

    fun filterItems(query: String) {
        itemFilter.postValue(query)
    }

    fun addFromJson(json: String): Job {
        return viewModelScope.launch {
            val memoList = parseJson(json)
            repository.insertAll(memoList)
        }
    }

    private fun parseJson(json: String): List<MemoModel> {
        val jsonObject = JSONObject(json)
        val memoList = mutableListOf<MemoModel>()
        val memos = jsonObject.getJSONArray("accounts")
        for (i in 0 until memos.length()) {
            val memo = memos.getJSONObject(i)
            val title = memo.getString("title")
            val subTitle = memo.optString("subTitle", "")
            val memoText = memo.getString("memo")
            memoList.add(MemoModel(0, title, subTitle, encryption.encrypt(memoText)))
        }
        return memoList
    }
}