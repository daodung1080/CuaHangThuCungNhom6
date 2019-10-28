package com.dung.nhom6cuahangthucung

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.lang.NumberFormatException

class ManagementActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_management)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    // Create option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_manager,menu)
        return true
    }

    // option item Function
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.menuAddPet){
            var alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Thêm thú cưng")
            alertDialog.setIcon(R.drawable.ic_logo)
            var view = layoutInflater.inflate(R.layout.dialog_add_pet, null)
            var edtName = view.findViewById<EditText>(R.id.edtName)
            var edtImage = view.findViewById<EditText>(R.id.edtImage)
            var edtPrice = view.findViewById<EditText>(R.id.edtPrice)
            var edtBreed = view.findViewById<EditText>(R.id.edtBreed)
            var edtDes = view.findViewById<EditText>(R.id.edtDes)

            var rdTinhTrang = view.findViewById<RadioButton>(R.id.rdTinhTrang)

            alertDialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                try {
                    var price = edtPrice.text.toString().toInt()
                    var breed = edtBreed.text.toString()
                    var des = edtDes.text.toString()
                    var name = edtName.text.toString()
                    var image = edtImage.text.toString()
                    if(image == null){
                        image = "https://achocanh.com/wp-content/uploads/2018/05/ch%C3%B3-ch%C4%83n-c%E1%BB%ABu-366x315.jpg"
                    }
                    if (price == null || breed == "" || des == "" || name == "") {
                        showMessage("Vui lòng không để trống thông tin")
                    } else {
                        if (rdTinhTrang.isChecked) {
                            addPetToDatabase(price,breed,des,"Con",image,name)
                        } else {
                            addPetToDatabase(price,breed,des,"Het",image,name)
                        }
                    }
                } catch (e: NumberFormatException) {

                }
            })

            alertDialog.setView(view)
            var dialog = alertDialog.create()
            dialog.show()
        }
        return true
    }

    private fun addPetToDatabase(price: Int, breed: String, des: String, tinhtrang: String, image: String, name: String) {
        var urlUpdate = "https://cuahangthucung.herokuapp.com/pet/add"
        Log.d("urlupdate", urlUpdate)
        val requestQueue = Volley.newRequestQueue(this)

        val params = HashMap<String,String>()
        params["name"] = name
        params["image"] = image
        params["price"] = price.toString()
        params["breed"] = breed
        params["exist"] = tinhtrang
        params["description"] = des
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
                        showMessage("Thêm thú cưng trên cửa hàng thành công")
                    } else {
                        showMessage("Thêm thú cưng trên cửa hàng không thành công. Vui lòng kiểm tra lại đường truyền")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Toast.makeText(this, "Vui lòng kiểm tra lại đường truyền", Toast.LENGTH_SHORT)
                    .show()
            }
        )

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
