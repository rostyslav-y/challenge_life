package com.challenge.screen.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.challenge.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(this, getString(R.string.main_toast_text_ui_tbd), Toast.LENGTH_LONG).show()
    }

}