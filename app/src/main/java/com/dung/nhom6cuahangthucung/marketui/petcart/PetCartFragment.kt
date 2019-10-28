package com.dung.nhom6cuahangthucung.marketui.petcart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dung.nhom6cuahangthucung.BaseFragment
import com.dung.nhom6cuahangthucung.Constants
import com.dung.nhom6cuahangthucung.R
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat

class PetCartFragment : BaseFragment() {

    lateinit var root: View
    lateinit var tvThongTin: TextView
    lateinit var tvPetName: TextView
    lateinit var tvPetGenre: TextView
    lateinit var imgPet: ImageView
    lateinit var btnBuy: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_pet_cart, container, false)
        initView()
        getData()
        getUserData()
        btnBuy.setOnClickListener {
            pushOrder()
        }
        return root
    }

    private fun pushOrder() {
        val requestQueue = Volley.newRequestQueue(context)
        var url =
            "https://cuahangthucung.herokuapp.com/cart/add/?username=${Constants.username}&petid=${Constants.cartID}"

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
                        showMessage("Đặt hàng thành công!")
                    } else {
                        showMessage("Đặt hàng thất bại. Hãy kiểm tra lại đường truyền")
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                showMessage("Vui lòng kiểm tra lại đường truyền")
            }
        )
        requestQueue.add(jsonObjectRequest)
    }


    private fun getData() {
        val requestQueue = Volley.newRequestQueue(context)
        var url = "https://cuahangthucung.herokuapp.com/pet/findbyid/${Constants.cartID}"

        // Initialize a new JsonObjectRequest instance
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONObject> { response ->
                try {
                    // Get the current student (json object) data
                    val id = response.getString("_id")
                    val name = response.getString("name")
                    val price = response.getInt("price")
                    val image = response.getString("image")
                    val breed = response.getString("breed")
                    val exist = response.getString("exist")
                    val des = response.getString("description")
                    tvPetName.text = name
                    tvPetGenre.text = "${DecimalFormat("###,###,###").format(price)} VND"
                    Picasso.get().load(image).into(imgPet)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                showMessage("Vui lòng kiểm tra lại đường truyền")
            }
        )

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest)
    }

    private fun initView() {
        tvThongTin = root.findViewById(R.id.tvThongTin)
        tvThongTin.text =
            "Khách hàng: ${Constants.username}\nSố điện thoại: 0991744443\nĐịa chỉ: Yên Hòa, Cầu Giấy\nThông tin khác: Rất yêu thú cưng đặc biệt là chó và mèo"
        tvPetName = root.findViewById(R.id.tvPetName)
        tvPetGenre = root.findViewById(R.id.tvPetGenre)
        imgPet = root.findViewById(R.id.imgPet)
        btnBuy = root.findViewById(R.id.btnBuy)
    }

    fun getUserData(){
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
                    var name = response.getString("hoten")
                    var email = response.getString("email")
                    var phoneNumber = response.getString("phoneNumber")
                    var address = response.getString("address")
                    tvThongTin.text = "Khách hàng: ${name}\nSố điện thoại: ${phoneNumber}\nĐịa chỉ: ${address}\nThông tin khác: ${email}"
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