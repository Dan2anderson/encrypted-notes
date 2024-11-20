package com.example.encryptednotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.encryptednotes.R
import com.google.android.material.textview.MaterialTextView

class ShowMemoDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_show_memo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val memo = arguments?.getString(ARG_MEMO) ?: ""
        val textView: MaterialTextView = view.findViewById(R.id.memoText)
        textView.text = memo
        val confirmButton: Button = view.findViewById(R.id.doneButton)

        confirmButton.setOnClickListener {
            dismiss()
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    companion object {
        const val ARG_MEMO = "memo"
        fun newInstance(memo: String): ShowMemoDialogFragment {
            val fragment = ShowMemoDialogFragment()
            val args = Bundle()
            args.putString(ARG_MEMO, memo)
            fragment.arguments = args
            return fragment
        }
    }

}