package com.shubham.uploadlibrary

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UploadDoc(private val context: Context) {

    // firebase storage reference
    private val storageRef: StorageReference = FirebaseStorage.getInstance().reference

    //  fun to upload document to firebase
    //  this function takes 3 parameters:
    //      userId: String - this is needed to add document in the folder of this user
    //      data: Intent - this is the data of the chosen document. it is needed to get uri of the document
    //      AlertDialog - this is optional, pass this to show uploading progress
    fun upload(userId: String, data: Intent?, progressDialog: AlertDialog? = null) {
        //show the progress dialog that document is uploading
        progressDialog?.show()

        //get the uri and upload it onto the firebase storage
        val uri = data?.data
        val childRef = storageRef.child(userId).child(uri?.lastPathSegment.toString())
        childRef.putFile(uri!!).addOnSuccessListener {

            //dismiss the uploading progress dialog and show success dialog
            progressDialog?.dismiss()
            showDialog("Success", "Your document is successfully uploaded!!!")
        }
            .addOnCanceledListener {
                //dismiss the uploading progress dialog and show error dialog
                progressDialog?.dismiss()
                showDialog("Error", "Some error occurred! Make sure your internet connection is stable!!")
            }
            .addOnFailureListener{
                //dismiss the uploading progress dialog and show error dialog
                progressDialog?.dismiss()
                showDialog("Error", "Some error occurred! Make sure your internet connection is stable!!")
            }
    }

    //this function takes two arguments: title and message to show in the progress dialog
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