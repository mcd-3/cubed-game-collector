package com.matthew.carvalhodagenais.cubedcollector

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1250)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}