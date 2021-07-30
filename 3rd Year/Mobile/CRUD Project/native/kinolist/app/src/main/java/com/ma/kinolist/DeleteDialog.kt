package com.ma.kinolist

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DeleteDialog : DialogFragment() {

    internal lateinit var listener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment?)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Delete this entry?")
                    .setPositiveButton("Delete",
                            DialogInterface.OnClickListener { dialog, id ->
                                listener.onDialogPositiveClick(this)
                            })
                    .setNegativeButton("Cancel",
                            DialogInterface.OnClickListener { dialog, id ->
                                dialog.dismiss()
                            })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
