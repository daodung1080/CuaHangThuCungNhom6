package com.dung.nhom6cuahangthucung.user

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dung.nhom6cuahangthucung.BaseActivity
import com.dung.nhom6cuahangthucung.Constants
import com.dung.nhom6cuahangthucung.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_pet_detail.*
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        checkData()

        txtRegister.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }

    private fun checkData() {
        btn_login.setOnClickListener {
            val requestQueue = Volley.newRequestQueue(this)
            var username = edtUsername.text.toString()
            var password = edtPassword.text.toString()
            var url =
                "https://cuahangthucung.herokuapp.com/user/login/?username=$username&password=$password"

            // Initialize a new JsonObjectRequest instance
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener<JSONObject> { response ->
                    try {
                        // Get the current student (json object) data
                        val result = response.getBoolean("result")
                        if (result == true) {
                            showMessage("Đăng nhập thành công!")
                            Constants.username = username
                            startActivity(Intent(this, HomeActivity::class.java))
                        } else {
                            showMessage("Đăng nhập thất bại do tài khoản chưa được đăng ký")
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()
                }
            )
            requestQueue.add(jsonObjectRequest)
        }
    }

}
