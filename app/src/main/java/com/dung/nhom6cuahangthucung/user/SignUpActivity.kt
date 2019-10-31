package com.dung.nhom6cuahangthucung.user

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dung.nhom6cuahangthucung.BaseActivity
import com.dung.nhom6cuahangthucung.Constants
import com.dung.nhom6cuahangthucung.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.edtPassword
import kotlinx.android.synthetic.main.activity_sign_up.edtUsername
import org.json.JSONException
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        back_to_login.setOnClickListener{
                startActivity(Intent(this, LoginActivity::class.java))
            }
        checkData()
    }

    private fun checkData() {
        btn_register.setOnClickListener {
            val requestQueue = Volley.newRequestQueue(this)
            var username = edtUsername.text.toString()
            var password = edtPassword.text.toString()
            if (username == "" && password == "") {
                showMessage("Vui lòng không để trống thông tin")
            }
            else if(username == "admin"){
                showMessage("Tên đăng nhập này đã được sử dụng")
            }
            else {
                if (edtConfirmPassword.text.toString() != password) {
                    showMessage("Vui lòng nhập lại mật khẩu đúng với ô trên")
                } else {
                    var url =
                        "https://cuahangthucung.herokuapp.com/user/signup/?username=$username&password=$password"

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
                                    showMessage("Đăng ký thành công!")
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    onBackPressed()
                                } else {
                                    showMessage("Tài khoản này đã được đăng ký trước đây")
                                }

                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        },
                        Response.ErrorListener {
                            Toast.makeText(this, "Vui lòng kiểm tra lại đường truyền", Toast.LENGTH_SHORT).show()
                        }
                    )
                    requestQueue.add(jsonObjectRequest)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
