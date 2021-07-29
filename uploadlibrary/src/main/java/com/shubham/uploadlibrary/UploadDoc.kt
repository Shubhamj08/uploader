package com.shubham.uploadlibrary

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.storage.StorageReference

class UploadDoc(context: Context) {
    private val context = context

    fun upload(storageRef: StorageReference, userId: String, data: Intent?, progressDialog: AlertDialog? = null) {
        progressDialog?.show()
        val uri = data?.data
        val childRef = storageRef.child(userId).child(uri?.lastPathSegment.toString())
        childRef.putFile(uri!!).addOnSuccessListener {
            progressDialog?.dismiss()
            showDialog("Success", "Your document is successfully uploaded!!!")
        }
    }

    fun showDialog(title: String, message: String){
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton("OK"){dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

}