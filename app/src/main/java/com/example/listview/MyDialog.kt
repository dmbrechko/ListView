package com.example.listview

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class MyDialog: DialogFragment() {
    private lateinit var removable: Removable

    override fun onAttach(context: Context) {
        super.onAttach(context)
        removable = context as Removable
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val error = "No argument supplied"
        val index = arguments?.getInt(KEY_USER_INDEX) ?: throw IllegalStateException(error)
        val name = arguments?.getString(KEY_USER_NAME) ?: throw IllegalStateException(error)
        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.confirm_deleting)
            .setMessage(String.format(getString(R.string.delete_user_with_name), name))
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
            .setPositiveButton(R.string.delete) { dialog, _ ->
                removable.remove(index)
                dialog.dismiss()
            }
            .create()
    }

    companion object {
        const val KEY_USER_INDEX = "key note index"
        const val KEY_USER_NAME = "key user name"
        const val TAG = "MyDialog"
    }
}