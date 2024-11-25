package com.bedrock.encryptednotes.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.bedrock.encryptednotes.R

class ImportJsonDialog: DialogFragment() {
    private lateinit var jsonCallback: JsonCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        jsonCallback = context as JsonCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.import_json_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText1: EditText = view.findViewById(R.id.jsonText)
        val confirmButton: Button = view.findViewById(R.id.confirmButton)

        confirmButton.setOnClickListener {
            val json = editText1.text.toString()
            jsonCallback.import(json)
            dismiss()
        }
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    interface JsonCallback {
        fun import(json: String)
    }
}