package com.dung.nhom6cuahangthucung.ui.petfind

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dung.nhom6cuahangthucung.Constants
import com.dung.nhom6cuahangthucung.R
import com.dung.nhom6cuahangthucung.adapter.FindAdapter
import com.dung.nhom6cuahangthucung.adapter.PetAdapter
import com.dung.nhom6cuahangthucung.model.Pet
import com.dung.nhom6cuahangthucung.user.PetDetailActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class PetFindFragment : Fragment() {

    lateinit var rvFind: RecyclerView
    lateinit var adapter: FindAdapter
    lateinit var list: ArrayList<Pet>
    lateinit var root: View
    lateinit var edtFind: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_pet_find, container, false)
        initView()
        getData()
        return root
    }

    private fun getData() {
        edtFind.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.length > 3){
                    list.clear()
                    adapter.notifyDataSetChanged()
                    var url = "https://cuahangthucung.herokuapp.com/pet/findone/$s"
                    getPetData(url)
                }
            }
        })
    }

    fun getPetData(url: String){
        val requestQueue = Volley.newRequestQueue(context)

        // Initialize a new JsonObjectRequest instance
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONObject> { response ->
                try {
                    val id = response.getString("_id")
                    val name = response.getString("name")
                    val price = response.getInt("price")
                    val image = response.getString("image")
                    val breed = response.getString("breed")
                    val exist = response.getString("exist")
                    val des = response.getString("description")
                    list.add(Pet(id, name, price, image, breed, exist, des))
                    adapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Toast.makeText(context, "That didn't work! ${it.toString()}", Toast.LENGTH_SHORT).show()
            }
        )

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest)
    }

    private fun initView() {
        rvFind = root.findViewById(R.id.rvFind)
        list = ArrayList()
        adapter = FindAdapter(context!!, list, this)
        rvFind.layoutManager = LinearLayoutManager(context)
        rvFind.adapter = adapter
        edtFind = root.findViewById(R.id.edtFind)
    }

    fun clickPet(position: Int) {
        Constants.petID = list.get(position).id
        startActivity(Intent(context, PetDetailActivity::class.java))
    }
}