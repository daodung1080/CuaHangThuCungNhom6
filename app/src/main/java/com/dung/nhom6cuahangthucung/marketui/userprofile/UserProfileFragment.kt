package com.dung.nhom6cuahangthucung.marketui.petfind

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dung.nhom6cuahangthucung.BaseFragment
import com.dung.nhom6cuahangthucung.Constants
import com.dung.nhom6cuahangthucung.R
import org.json.JSONException
import org.json.JSONObject

class UserProfileFragment : BaseFragment() {

    lateinit var tvName: TextView
    lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_user_profile, container, false)
        initView()
        getData()
        return root
    }

    private fun initView() {
        tvName = root.findViewById(R.id.txtProfileUsername)
    }

    private fun getData() {
        val requestQueue = Volley.newRequestQueue(context)
        var username = Constants.username
        var url =
            "https://cuahangthucung.herokuapp.com/user/findone/$username"

        // Initialize a new JsonObjectRequest instance
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONObject> { response ->
                try {
                    // Get the current student (json object) data
                    tvName.text = response.getString("username")

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                showMessage("Vui lòng kiểm tra lại đường truyền ${it.toString()}")
            }
        )
        requestQueue.add(jsonObjectRequest)
    }
}