package com.elmirov.shiftlabtesttask.utill

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.elmirov.shiftlabtesttask.R

fun showDialog(
    context: Context,
    onClick: () -> Unit,
    title: String,
    message: String,
) {
    AlertDialog.Builder(context).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(context.getString(R.string.ok)) { _, _ ->
            onClick()
        }
        setOnDismissListener {
            onClick()
        }
        create()
        show()
    }
}