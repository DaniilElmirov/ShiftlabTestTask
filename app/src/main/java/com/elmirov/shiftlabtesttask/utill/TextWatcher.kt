package com.elmirov.shiftlabtesttask.utill

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

class MultiTextWatcher(
    private val textInputEditTexts: List<TextInputEditText>,
    private val textChanged: () -> Unit,
    private val allFilled: (Boolean) -> Unit,
) : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        textChanged()
    }

    override fun afterTextChanged(p0: Editable?) {
        for (editText in textInputEditTexts) {
            if (editText.text.isNullOrBlank()) {
                allFilled(false)
                return
            }
        }
        allFilled(true)
    }
}