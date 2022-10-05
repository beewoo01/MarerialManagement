package com.raonsoft.matmanagement.extention

import android.util.Log
import android.widget.TextView

fun TextView.setNullCheckText(vararg str : List<String?>) {

    var usefulStr = str.joinToString()
    usefulStr = usefulStr.replace("[","")
    usefulStr = usefulStr.replace(",","")
    usefulStr = usefulStr.replace("]","")
    usefulStr = usefulStr.replace("null","")
    usefulStr = usefulStr.replace("Null","")

    text = usefulStr

}

fun String.nullCheck(text : String?) {
    if (text != null && !text.equals("null", true)) {

    }
}