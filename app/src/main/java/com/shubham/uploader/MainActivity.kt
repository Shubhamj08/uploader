package com.shubham.uploader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.shubham.uploadlibrary.UploadDoc

class MainActivity : AppCompatActivity() {
    private lateinit var uploadButton: MaterialButton
    private lateinit var storageRef: StorageReference
    private lateinit var progressDialog: AlertDialog
    private lateinit var spinner: Spinner
    private lateinit var name: EditText
    private lateinit var nameField: TextInputLayout
    private val UPLOAD_DOC = 1

    val uploader = UploadDoc(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uploadButton = findViewById(R.id.uploadButton)
        name = findViewById(R.id.nameText)
        nameField = findViewById(R.id.nameField)

        storageRef = FirebaseStorage.getInstance().reference

        val progressBar = ProgressBar(this)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        progressBar.layoutParams = lp

        progressDialog = MaterialAlertDialogBuilder(this)
                .setTitle("Uploading...")
                .setMessage("Your document is getting uploaded...")
                .setView(progressBar)
                .create()

        spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.doc_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        uploadButton.setOnClickListener {

            if(name.text.toString().isEmpty()){
                nameField.error = getString(R.string.enter_username)
            }
            else{
                nameField.error = null
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*, application/pdf"
                startActivityForResult(intent, UPLOAD_DOC)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == UPLOAD_DOC) {
            uploader.upload(storageRef, name.text.toString(), data, progressDialog)

        } else if (resultCode != RESULT_CANCELED) {
            uploader.showDialog("Error", "Some Error Occured")
        }
    }
}