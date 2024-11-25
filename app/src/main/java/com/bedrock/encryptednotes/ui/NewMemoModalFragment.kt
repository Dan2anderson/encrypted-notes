package com.bedrock.encryptednotes.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.bedrock.encryptednotes.R

class NewMemoModalFragment : DialogFragment() {
    private lateinit var dataCallback: DataCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataCallback = context as DataCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_new_memo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayout: LinearLayout = view.findViewById(R.id.newMemoLayout)
        val displayMetrics = resources.displayMetrics
        val width = (displayMetrics.widthPixels * 0.8).toInt()
        linearLayout.layoutParams.width = width

        val editText1: EditText = view.findViewById(R.id.editText1)
        val editText2: EditText = view.findViewById(R.id.editText2)
        val editText3: EditText = view.findViewById(R.id.editText3)
        val confirmButton: Button = view.findViewById(R.id.confirmButton)

        confirmButton.setOnClickListener {
            val title = editText1.text.toString()
            val subTitle = editText2.text.toString()
            val memo = editText3.text.toString()
            dataCallback.dataCallback(title, subTitle, memo)
            dismiss()
        }
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    
    interface DataCallback {
        fun dataCallback(title: String, subTitle: String, memo: String)
    }
}