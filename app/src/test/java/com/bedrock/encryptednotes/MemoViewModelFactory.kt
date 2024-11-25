package com.bedrock.encryptednotes.viewmodel

import androidx.lifecycle.ViewModel
import com.bedrock.encryptednotes.data.MemoRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class MemoViewModelFactoryTest {

    private lateinit var factory: MemoViewModelFactory
    private lateinit var repository: MemoRepository

    @Before
    fun setUp() {
        repository = mock(MemoRepository::class.java)
        factory = MemoViewModelFactory(repository)
    }


    @Test(expected = IllegalArgumentException::class)
    fun `create should throw IllegalArgumentException for unknown ViewModel class`() {
        factory.create(UnknownViewModel::class.java)
    }

    class UnknownViewModel : ViewModel()
}