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
import com.dung.nhom6cuahangthucung.R
import com.dung.nhom6cuahangthucung.model.Pet
import com.dung.nhom6cuahangthucung.marketui.petfind.PetFindFragment
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class FindAdapter(var context: Context, var list: ArrayList<Pet>, var fragment: PetFindFragment) :
    RecyclerView.Adapter<FindAdapter.FindHolder>() {

    override fun onBindViewHolder(holder: FindHolder, p1: Int) {
        var pet = list.get(p1)
        val fm = DecimalFormat("###,###,###")
        holder.tvPetName.text = pet.name
        holder.tvPetGenre.text = "Trạng thái: " + pet.exist
        holder.tvPrice.text = "${fm.format(pet.price)} VND"
        Picasso.get().load(pet.image).into(holder.imgPet)
        holder.imgBuy.setOnClickListener {
            Toast.makeText(context, "Cho vào giỏ hàng ${pet.name} thành công!", Toast.LENGTH_SHORT)
                .show()
        };
        holder.imgLove.setOnClickListener {
            Toast.makeText(context, "Yêu thích ${pet.name} thành công!", Toast.LENGTH_SHORT).show()
        }
        holder.cvPet.setOnClickListener {
            fragment.clickPet(p1)
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FindAdapter.FindHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.list_item_pet, p0, false)
        return FindHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class FindHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvPetName = view.findViewById<TextView>(R.id.tvPetName)
        var tvPetGenre = view.findViewById<TextView>(R.id.tvPetGenre)
        var tvPrice = view.findViewById<TextView>(R.id.tvPrice)
        var imgPet = view.findViewById<ImageView>(R.id.imgPet)
        var imgLove = view.findViewById<ImageView>(R.id.imgLove)
        var imgBuy = view.findViewById<ImageView>(R.id.imgBuy)
        var cvPet = view.findViewById<CardView>(R.id.cvPet)
    }
}