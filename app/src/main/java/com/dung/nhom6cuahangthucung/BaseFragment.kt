package com.dung.nhom6cuahangthucung

import android.support.v4.app.Fragment
import android.widget.Toast

open class BaseFragment: Fragment() {

    fun showMessage(message: String){
        Toast.makeText(context!!, message, Toast.LENGTH_SHORT).show()
    }

}