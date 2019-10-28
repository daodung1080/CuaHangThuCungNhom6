package com.dung.nhom6cuahangthucung.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.dung.nhom6cuahangthucung.Constants
import com.dung.nhom6cuahangthucung.R
import com.dung.nhom6cuahangthucung.model.Pet
import com.dung.nhom6cuahangthucung.model.User
import com.dung.nhom6cuahangthucung.ui.gallery.GalleryFragment
import com.dung.nhom6cuahangthucung.ui.home.HomeFragment
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class UserManagerAdapter(
    var context: Context,
    var list: ArrayList<User>,
    var fragmentMartket: GalleryFragment
) : RecyclerView.Adapter<UserManagerAdapter.PetHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PetHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.list_item_user, p0, false)
        return PetHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PetHolder, p1: Int) {
        var pet = list.get(p1)
        val fm = DecimalFormat("###,###,###")
        holder.tvPetName.text = "Username: "+pet.username
        holder.tvPetGenre.text = "Password: " + pet.password
        holder.imgLove.setOnClickListener {
            fragmentMartket.removeUser(p1)
        }
        //hello
    }

    class PetHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvPetName = view.findViewById<TextView>(R.id.tvPetName)
        var tvPetGenre = view.findViewById<TextView>(R.id.tvPetGenre)
        var imgPet = view.findViewById<ImageView>(R.id.imgPet)
        var imgLove = view.findViewById<ImageView>(R.id.imgLove)
    }
}