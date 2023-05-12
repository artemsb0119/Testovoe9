package com.example.testovoe9

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide

class SettingsActivity : AppCompatActivity() {

    private lateinit var imageViewFon2: ImageView
    private lateinit var buttonClear: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        buttonClear = findViewById(R.id.buttonClear)
        imageViewFon2 = findViewById(R.id.imageViewFon2)

        Glide.with(this)
            .load("http://135.181.248.237/9/fon2.png")
            .into(imageViewFon2)

        buttonClear.setOnClickListener {
            val editor = getSharedPreferences("my_preferences", Context.MODE_PRIVATE).edit()
            editor.putInt("max", 0)
            editor.apply()
        }
    }
}