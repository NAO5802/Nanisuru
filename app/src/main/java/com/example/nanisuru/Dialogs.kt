package com.example.nanisuru

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ItemDeleteDialog : DialogFragment() {
    interface Listener {
        fun deleteItem()
    }

    private var listener: Listener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is Listener -> listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage("アクティビティを削除します。\nよろしいですか？")
        builder.setPositiveButton("OK") { dialog, which ->
            listener?.deleteItem()
        }
        builder.setNegativeButton("キャンセル") { dialog, which ->

        }
        return builder.create()
    }
}