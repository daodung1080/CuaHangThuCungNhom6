package com.dung.nhom6cuahangthucung.ui.send

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import com.dung.nhom6cuahangthucung.R
import com.dung.nhom6cuahangthucung.user.LoginActivity

class SendFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_send, container, false)
        startActivity(Intent(context,LoginActivity::class.java))
        activity!!.finish()
        return root
    }
}