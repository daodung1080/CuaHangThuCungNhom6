package com.dung.nhom6cuahangthucung.user

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dung.nhom6cuahangthucung.BaseActivity
import com.dung.nhom6cuahangthucung.Constants
import com.dung.nhom6cuahangthucung.R
import com.dung.nhom6cuahangthucung.model.Pet
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pet_detail.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat

class PetDetailActivity : BaseActivity() {

    var petID = Constants.petID
    var url = "https://cuahangthucung.herokuapp.com/pet/findbyid/$petID"
    var cartID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_detail)
        supportActionBar!!.setHomeButtonEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);

        getData()
        onBuyClick()

    }

    private fun onBuyClick() {
        btnBuy.setOnClickListener {
            Constants.cartID = cartID
            showMessage("Cho thú cưng vào giỏ hàng thành công! Bạn hãy ra giỏ hàng để xem nhé")
        }
    }

    private fun getData() {
        val requestQueue = Volley.newRequestQueue(this)

        // Initialize a new JsonObjectRequest instance
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONObject> { response ->
                try {
                    // Get the current student (json object) data
                    val id = response.getString("_id")
                    cartID = id
                    val name = response.getString("name")
                    val price = response.getInt("price")
                    val image = response.getString("image")
                    val breed = response.getString("breed")
                    val exist = response.getString("exist")
                    val des = response.getString("description")
                    tvDes.text = des
                    tvDetailPetName.text = name
                    tvDetailPetPrice.text = "${DecimalFormat("###,###,###").format(price)} VND"
                    Picasso.get().load(image).into(imgDetailPet)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Toast.makeText(this, "Vui lòng kiểm tra lại đường truyền", Toast.LENGTH_SHORT).show()
            }
        )

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest)
    }
}
