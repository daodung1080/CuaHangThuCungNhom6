package com.dung.nhom6cuahangthucung.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dung.nhom6cuahangthucung.BaseFragment
import com.dung.nhom6cuahangthucung.R
import com.dung.nhom6cuahangthucung.adapter.InvoiceManagerAdapter
import com.dung.nhom6cuahangthucung.adapter.PetManagerAdapter
import com.dung.nhom6cuahangthucung.model.Invoice
import com.dung.nhom6cuahangthucung.model.Pet
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SlideshowFragment : BaseFragment() {

    lateinit var root: View
    lateinit var rvPet: RecyclerView
    lateinit var adapter: InvoiceManagerAdapter
    lateinit var list: ArrayList<Invoice>

    var url = "https://cuahangthucung.herokuapp.com/cart/getall"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_slideshow, container, false)
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
                        val username = jObject.getString("username")
                        val petid = jObject.getString("petid")
                        var done = jObject.getBoolean("done")

                        if (done == false) {
                            list.add(Invoice(id, username, petid))
                        }

                    }
                    adapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Toast.makeText(context, "Vui lòng kiểm tra lại đường truyền", Toast.LENGTH_SHORT).show()
            }
        )

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest)
    }

    private fun initView() {
        rvPet = root.findViewById(R.id.rvInvoice)
        list = ArrayList()
        adapter = InvoiceManagerAdapter(context!!, list, this)
        rvPet.layoutManager = LinearLayoutManager(context)
        rvPet.adapter = adapter
    }

    fun checkInvoice(position: Int) {
        var invoice = list.get(position)
        checkInvoiceInDatabase(invoice.id)
    }

    private fun checkInvoiceInDatabase(id: String) {
        var urlRemove = "https://cuahangthucung.herokuapp.com/cart/buy/?id=${id}"
        val requestQueue = Volley.newRequestQueue(context)

        // Initialize a new JsonObjectRequest instance
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            urlRemove,
            null,
            Response.Listener<JSONObject> { response ->
                try {
                    var result = response.getBoolean("result")
                    if (result == true){
                        showMessage("Chốt hóa đơn thành công")
                        getData()
                    }else {
                        showMessage("Chốt hóa đơn không thành công. Vui lòng kiểm tra lại đường truyền")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Toast.makeText(context, "Vui lòng kiểm tra lại đường truyền ${it.toString()}", Toast.LENGTH_SHORT).show()
            }
        )

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest)
    }
}