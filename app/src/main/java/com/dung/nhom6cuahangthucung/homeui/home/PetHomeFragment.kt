package com.dung.nhom6cuahangthucung.homeui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import com.dung.nhom6cuahangthucung.Constants
import com.dung.nhom6cuahangthucung.R
import com.dung.nhom6cuahangthucung.user.PetListActivity

class PetHomeFragment : Fragment() {

    lateinit var llDog: CardView
    lateinit var llCat: CardView
    lateinit var llFish: CardView
    lateinit var llBird: CardView

    var petInstance = "thu"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_pet_home, container, false)
        llDog = root.findViewById(R.id.llDog)
        llDog.setOnClickListener {
            changeActivity("dog")
        }
        llCat = root.findViewById(R.id.llCat)
        llCat.setOnClickListener {
            changeActivity("cat")
        }
        llFish = root.findViewById(R.id.llFish)
        llFish.setOnClickListener {
            changeActivity("fish")
        }
        llBird = root.findViewById(R.id.llBird)
        llBird.setOnClickListener {
            changeActivity("bird")
        }
        return root
    }

    fun changeActivity(values: String){
        var intent = Intent(context, PetListActivity::class.java)
        Constants.petGenre = values
        startActivity(intent)
    }

}