package com.example.encryptednotes.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.encryptednotes.R
import com.google.android.material.textview.MaterialTextView

class DeleteDialog: DialogFragment() {

    private lateinit var deleteCallback: DeleteCallback
    var itemId = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        deleteCallback = context as DeleteCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_delete_memo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString(ARG_TITLE) ?: ""
        itemId = arguments?.getInt(ARG_ID) ?: 0

        val cancelButton: Button = view.findViewById(R.id.cancelButton)
        cancelButton.setBackgroundColor(ContextCompat.getColor(requireContext(),
            R.color.redExtraLight
        ))
        val confirmButton: Button = view.findViewById(R.id.confirmButton)
        val notice: MaterialTextView = view.findViewById(R.id.noticeText)
        notice.text = getString(R.string.delete_confirmation, title)
        confirmButton.setOnClickListener {
            deleteCallback.delete(itemId)
            dismiss()
        }
        cancelButton.setOnClickListener {
            dismiss()
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    companion object {
        const val ARG_TITLE = "title_insert"
        const val ARG_ID = "model_id"
        fun newInstance(title: String, id: Int): DeleteDialog {
            val fragment = DeleteDialog()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putInt(ARG_ID, id)
            fragment.arguments = args
            return fragment
        }
    }

    interface DeleteCallback {
        fun delete(itemId: Int)
    }

}