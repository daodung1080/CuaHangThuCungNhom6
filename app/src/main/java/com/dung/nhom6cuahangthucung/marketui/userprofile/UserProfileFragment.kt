package com.dung.nhom6cuahangthucung.marketui.petfind

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dung.nhom6cuahangthucung.BaseFragment
import com.dung.nhom6cuahangthucung.Constants
import com.dung.nhom6cuahangthucung.R
import org.json.JSONException
import org.json.JSONObject
import java.lang.NumberFormatException

class UserProfileFragment : BaseFragment() {

    lateinit var tvName: TextView
    lateinit var tvEmail: TextView
    lateinit var tvPhone: TextView
    lateinit var tvAddress: TextView
    lateinit var txtProfileUsername: TextView
    lateinit var imgEdit: ImageView
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
        tvName = root.findViewById(R.id.tvName)
        tvEmail = root.findViewById(R.id.tvEmail)
        tvPhone = root.findViewById(R.id.tvPhone)
        tvAddress = root.findViewById(R.id.tvAddress)
        txtProfileUsername = root.findViewById(R.id.txtProfileUsername)
        imgEdit = root.findViewById(R.id.imgEdit)
        imgEdit.setOnClickListener {

            createDialogEdit()

        }
    }

    private fun createDialogEdit() {
        var alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Chỉnh sửa thông tin cá nhân")
        alertDialog.setIcon(R.drawable.ic_logo)
        var view = layoutInflater.inflate(R.layout.dialog_user_edit, null)
        var edtName = view.findViewById<EditText>(R.id.edtName)
        var edtEmail = view.findViewById<EditText>(R.id.edtEmail)
        var edtPhone = view.findViewById<EditText>(R.id.edtPhone)
        var edtAddress = view.findViewById<EditText>(R.id.edtAddress)

        edtName.setText(tvName.text.toString())
        edtEmail.setText(tvEmail.text.toString())
        edtPhone.setText(tvPhone.text.toString())
        edtAddress.setText(tvAddress.text.toString())

        alertDialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            var name = edtName.text.toString()
            var email = edtEmail.text.toString()
            var phone = edtPhone.text.toString()
            var address = edtAddress.text.toString()
            if (name == "" || email == "" || phone == "" || address == "") {
                showMessage("Vui lòng không để trống thông tin")
            } else {
                sendInformationToDatabase(name,email,phone,address)
            }
        })

        alertDialog.setView(view)
        var dialog = alertDialog.create()
        dialog.show()
    }

    private fun sendInformationToDatabase(name: String, email: String, phone: String, address: String) {
        var urlUpdate = "https://cuahangthucung.herokuapp.com/user/edit"
        val requestQueue = Volley.newRequestQueue(context)

        val params = HashMap<String,String>()
        params["name"] = name
        params["address"] = address
        params["phone"] = phone
        params["email"] = email
        params["id"] = Constants.username
        val jsonObject = JSONObject(params)

        // Initialize a new JsonObjectRequest instance
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            urlUpdate,
            jsonObject,
            Response.Listener<JSONObject> { response ->
                try {
                    var result = response.getBoolean("result")
                    if (result == true) {
                        showMessage("Chỉnh sửa thông tin cá nhân thành công")
                        getData()
                    } else {
                        showMessage("Chỉnh sửa thông tin cá nhân không thành công. Vui lòng kiểm tra lại đường truyền")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Toast.makeText(context, "Vui lòng kiểm tra lại đường truyền", Toast.LENGTH_SHORT)
                    .show()
            }
        )

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest)
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
                    txtProfileUsername.text = response.getString("username")
                    tvName.text = response.getString("hoten")
                    tvEmail.text = response.getString("email")
                    tvPhone.text = response.getString("phoneNumber")
                    tvAddress.text = response.getString("address")
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