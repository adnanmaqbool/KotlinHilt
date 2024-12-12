package com.adnan.kotlinhilt

import android.content.Context
import android.widget.Toast

fun String?.valueQualifier(): String {
    if (this == null)
        return "--"
    else if (this == "")
        return "--"
    else if (this == "null")
        return "--"
    else
        return this
}


fun String?.nullToEmpty(): String {
    if (this == null)
        return ""
    else if (this == "")
        return ""
    else
        return this
}

fun Context.showSuccessMsg(message: String = "Feature in progress") {
    if (message == null)
        Toast.makeText(this, "Feature in progress", Toast.LENGTH_SHORT).show()
    else
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

}


fun Context.showErrorMsg(message: String? = "Error") {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}





