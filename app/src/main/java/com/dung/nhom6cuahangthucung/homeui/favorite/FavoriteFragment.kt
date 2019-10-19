package com.dung.nhom6cuahangthucung.homeui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.dung.nhom6cuahangthucung.Constants
import com.dung.nhom6cuahangthucung.R
import com.dung.nhom6cuahangthucung.adapter.FavoriteAdapter
import com.dung.nhom6cuahangthucung.adapter.PetAdapter
import com.dung.nhom6cuahangthucung.model.Pet
import com.dung.nhom6cuahangthucung.user.PetDetailActivity
import org.json.JSONArray
import org.json.JSONException

class FavoriteFragment : Fragment() {
    lateinit var rvFavorite: RecyclerView
    lateinit var adapter: FavoriteAdapter
    lateinit var list: ArrayList<Pet>
    lateinit var root: View
    var url = "http://cuahangthucung.herokuapp.com/pet/getfavorite"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_gallery, container, false)
        initView()
        getPetData()
        return root
    }

    private fun initView() {
        rvFavorite = root.findViewById(R.id.rvFavorite)
        list = ArrayList()
        adapter = FavoriteAdapter(context!!, list, this)
        rvFavorite.layoutManager = LinearLayoutManager(context)
        rvFavorite.adapter = adapter
    }

    fun clickPet(position: Int) {
        Constants.petID = list.get(position).id
        startActivity(Intent(context, PetDetailActivity::class.java))
    }

    private fun getPetData() {
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
                Toast.makeText(context, "That didn't work!", Toast.LENGTH_SHORT).show()
            }
        )

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest)
    }
}