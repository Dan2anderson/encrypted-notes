package com.example.encryptednotes.misc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import com.example.encryptednotes.R
import com.example.encryptednotes.data.MemoModel

class MemoAdapter(private val itemList: MutableList<MemoModel>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<MemoAdapter.ItemViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: MemoModel)
        fun onLongClick(item: MemoModel)
    }

    @VisibleForTesting
    fun getItemList(): List<MemoModel> {
        return itemList
    }

    fun updateList(filteredList: List<MemoModel>){
        itemList.clear()
        itemList.addAll(filteredList)
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val subtitle: TextView = itemView.findViewById(R.id.subTitle)
        val description: TextView = itemView.findViewById(R.id.memo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_memo, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.title.text = item.title
        holder.subtitle.text = item.subTitle
        holder.description.text = if(item.memo.length<20) "⬤ ⬤ ⬤ ⬤ ⬤" else "⬤ ⬤ ⬤ ⬤ ⬤ . . ."
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(item)
        }
        holder.itemView.setOnLongClickListener {
            itemClickListener.onLongClick(item)
            return@setOnLongClickListener true
        }
    }


    override fun getItemCount() = itemList.size
}