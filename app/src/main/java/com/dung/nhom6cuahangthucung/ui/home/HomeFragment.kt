package com.dung.nhom6cuahangthucung.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dung.nhom6cuahangthucung.BaseActivity
import com.dung.nhom6cuahangthucung.BaseFragment
import com.dung.nhom6cuahangthucung.Constants
import com.dung.nhom6cuahangthucung.R
import com.dung.nhom6cuahangthucung.adapter.PetAdapter
import com.dung.nhom6cuahangthucung.adapter.PetManagerAdapter
import com.dung.nhom6cuahangthucung.model.Pet
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.NumberFormatException

class HomeFragment : BaseFragment() {

    lateinit var root: View
    lateinit var rvPet: RecyclerView
    lateinit var adapter: PetManagerAdapter
    lateinit var list: ArrayList<Pet>

    var clickedPet = "dog"
    var url = "https://cuahangthucung.herokuapp.com/pet/getall"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)
        initView()
        getData()
        return root
    }

    private fun getData() {
        list.clear()
        val requestQueue = Volley.newRequestQueue(context)

        // Initialize a new JsonObjectRequest instance
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONArray> { response ->
                try {
                    // Get the JSON array
                    // Loop through the array elements
                    for (i in 0 until response.length()) {
                        // Get current json object
                        val jObject = response.getJSONObject(i)

                        // Get the current student (json object) data
                        val id = jObject.getString("_id")
                        val name = jObject.getString("name")
                        val price = jObject.getInt("price")
                        val image = jObject.getString("image")
                        val breed = jObject.getString("breed")
                        val exist = jObject.getString("exist")
                        val des = jObject.getString("description")

                        list.add(Pet(id, name, price, image, breed, exist, des))

                    }
                    adapter.notifyDataSetChanged()
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

    private fun initView() {
        rvPet = root.findViewById(R.id.rvPet)
        list = ArrayList()
        adapter = PetManagerAdapter(context!!, list, this)
        rvPet.layoutManager = LinearLayoutManager(context)
        rvPet.adapter = adapter
    }

    fun removePet(position: Int) {
        var pet = list.get(position)
        removePetFromDatabase(pet.id)
    }

    private fun removePetFromDatabase(id: String) {
        var urlRemove = "https://cuahangthucung.herokuapp.com/pet/remove/?id=${id}"
        val requestQueue = Volley.newRequestQueue(context)

        // Initialize a new JsonObjectRequest instance
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            urlRemove,
            null,
            Response.Listener<JSONObject> { response ->
                try {
                    var result = response.getBoolean("result")
                    if (result == true) {
                        showMessage("Xóa thú cưng trên cửa hàng thành công")
                        getData()
                    } else {
                        showMessage("Xóa thú cưng trên cửa hàng không thành công. Vui lòng kiểm tra lại đường truyền")
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

    fun updatePet(position: Int) {
        var pet = list.get(position)

        var alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Chỉnh sửa thông tin thú cưng")
        alertDialog.setIcon(R.drawable.ic_logo)
        var view = layoutInflater.inflate(R.layout.dialog_pet_edit, null)
        var edtPrice = view.findViewById<EditText>(R.id.edtPrice)
        var edtBreed = view.findViewById<EditText>(R.id.edtBreed)
        var edtDes = view.findViewById<EditText>(R.id.edtDes)

        edtPrice.setText(pet.price.toString())
        edtBreed.setText(pet.breed)
        edtDes.setText(pet.des)

        var rdTinhTrang = view.findViewById<RadioButton>(R.id.rdTinhTrang)

        alertDialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            try {
                var price = edtPrice.text.toString().toInt()
                var breed = edtBreed.text.toString()
                var des = edtDes.text.toString()
                if (price == null || breed == "" || des == "") {
                    showMessage("Vui lòng không để trống thông tin")
                } else {
                    if (rdTinhTrang.isChecked) {
                        updatePetFromDatabase(pet.id,price,breed,des,"Con")
                    } else {
                        updatePetFromDatabase(pet.id,price,breed,des,"Het")
                    }
                }
            } catch (e: NumberFormatException) {

            }
        })

        alertDialog.setView(view)
        var dialog = alertDialog.create()
        dialog.show()
    }

    private fun updatePetFromDatabase(id: String, price: Int, breed: String, des: String, tinhtrang: String) {
        var urlUpdate = "https://cuahangthucung.herokuapp.com/pet/edit/?id=${id}&price=$price&breed=$breed&exist=$tinhtrang"
        Log.d("urlupdate", urlUpdate)
        val requestQueue = Volley.newRequestQueue(context)

        // Initialize a new JsonObjectRequest instance
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            urlUpdate,
            null,
            Response.Listener<JSONObject> { response ->
                try {
                    var result = response.getBoolean("result")
                    if (result == true) {
                        showMessage("Chỉnh sửa thú cưng trên cửa hàng thành công")
                        getData()
                    } else {
                        showMessage("Chỉnh sửa thú cưng trên cửa hàng không thành công. Vui lòng kiểm tra lại đường truyền")
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
}