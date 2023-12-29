package com.test.gitsubs.utils.extentions

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

internal fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(activity, message, duration).show()
}

internal fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}