package com.dung.nhom6cuahangthucung

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dung.nhom6cuahangthucung.user.LoginActivity

class SpashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 2000 //2sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_screen)
        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}
