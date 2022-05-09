package com.diatomicsoft.digitalbancharampur.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.util.*


fun ContentResolver.getFileName(fileUri: Uri, id: Int): String {
    /* val returnCursor = this.query(fileUri, null, null, null, null)
     if (returnCursor != null) {
         val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
         returnCursor.moveToFirst()
        // name = returnCursor.getString(nameIndex)

         returnCursor.close()
     }*/
    return Date().time.toString() + id
}

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun View.toastSnack(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_LONG).show()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}
