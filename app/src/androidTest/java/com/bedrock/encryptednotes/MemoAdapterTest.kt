package com.bedrock.encryptednotes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bedrock.encryptednotes.data.MemoModel
import com.bedrock.encryptednotes.misc.MemoAdapter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MemoAdapterTest {

    private lateinit var adapter: MemoAdapter
    private lateinit var itemList: MutableList<MemoModel>

    @Before
    fun setUp() {
        itemList = mutableListOf(
            MemoModel(1, "Title 1", "Subtitle 1", "Memo 1"),
            MemoModel(2, "Title 2", "Subtitle 2", "Memo 2")
        )
        adapter = MemoAdapter(itemList, object : MemoAdapter.OnItemClickListener {
            override fun onItemClick(item: MemoModel) {
                // Handle item click
            }

            override fun onLongClick(item: MemoModel) {
                // Handle item long click
            }
        })
    }

    @Test
    fun getItemCount() {
        assertEquals(2, adapter.itemCount)
    }

    @Test
    fun onCreateViewHolder() {
        val parent = LayoutInflater.from(ApplicationProvider.getApplicationContext())
            .inflate(R.layout.layout_memo, null) as ViewGroup
        val viewHolder = adapter.onCreateViewHolder(parent, 0)
        assert(viewHolder is MemoAdapter.ItemViewHolder)
    }

    @Test
    fun onBindViewHolder() {
        val parent = LayoutInflater.from(ApplicationProvider.getApplicationContext())
            .inflate(R.layout.layout_memo, null) as ViewGroup
        val viewHolder = adapter.onCreateViewHolder(parent, 0)
        adapter.onBindViewHolder(viewHolder, 0)

        val title = viewHolder.itemView.findViewById<TextView>(R.id.title)
        val subtitle = viewHolder.itemView.findViewById<TextView>(R.id.subTitle)
        val description = viewHolder.itemView.findViewById<TextView>(R.id.memo)

        assertEquals("Title 1", title.text)
        assertEquals("Subtitle 1", subtitle.text)
        assertEquals("⬤ ⬤ ⬤ ⬤ ⬤", description.text)
    }

    @Test
    fun updateList() {
        val newList = listOf(
            MemoModel(3, "Title 3", "Subtitle 3", "Memo 3"),
            MemoModel(4, "Title 4", "Subtitle 4", "Memo 4")
        )
        adapter.updateList(newList)
        assertEquals(2, adapter.itemCount)
        assertEquals("Title 3", adapter.getItemList()[0].title)
        assertEquals("Title 4", adapter.getItemList()[1].title)
    }
}