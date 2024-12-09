package com.bedrock.encryptednotes.ui

import android.app.AlertDialog
import android.content.Context


fun Context.showGenericDialog(title: String, message: String, buttonText: String, onButtonClick: () -> Unit) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton(buttonText) { dialog, _ ->
        onButtonClick()
        dialog.dismiss()
    }
    val dialog = builder.create()
    dialog.show()
}
