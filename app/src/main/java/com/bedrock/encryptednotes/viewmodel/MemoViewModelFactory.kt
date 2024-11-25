package com.bedrock.encryptednotes.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bedrock.encryptednotes.data.MemoRepository
import com.bedrock.encryptednotes.misc.Encryption

//class MemoViewModelFactory(private val memoDao: MemoDao) : ViewModelProvider.Factory {
class MemoViewModelFactory(private val repository: MemoRepository) : ViewModelProvider.Factory {
//    val memoDao = MemoDatabase.getDatabase(context).memoDao()
private val encryption: Encryption = Encryption()
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemoViewModel(repository, encryption) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}