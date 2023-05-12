package com.example.testovoe9

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide

class MenuActivity : AppCompatActivity() {

    private lateinit var buttonFacts: AppCompatButton
    private lateinit var buttonAchievements: AppCompatButton
    private lateinit var buttonTrue: AppCompatButton
    private lateinit var buttonSettings: AppCompatButton
    private lateinit var imageViewFon2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        buttonFacts = findViewById(R.id.buttonFacts)
        buttonAchievements = findViewById(R.id.buttonAchievements)
        buttonTrue = findViewById(R.id.buttonTrue)
        buttonSettings = findViewById(R.id.buttonSettings)
        imageViewFon2 = findViewById(R.id.imageViewFon2)

        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        var currentNumber = sharedPreferences.getInt("currentNumber", 1)
        if(currentNumber==1) {
            Glide.with(this)
                .load("http://135.181.248.237/9/fon2.png")
                .into(imageViewFon2)
        } else if (currentNumber==2){
            Glide.with(this)
                .load("http://135.181.248.237/7/fon3.png")
                .into(imageViewFon2)
        } else if (currentNumber==3){
            Glide.with(this)
                .load("http://135.181.248.237/7/fon4.png")
                .into(imageViewFon2)
        }

        buttonFacts.setOnClickListener {
            val intent = Intent(this, FactsActivity::class.java)
            startActivity(intent)
        }
        buttonAchievements.setOnClickListener {
            val intent = Intent(this, AchievementActivity::class.java)
            startActivity(intent)
        }
        buttonTrue.setOnClickListener {
            val intent = Intent(this, TrueActivity::class.java)
            startActivity(intent)
            finish()
        }
        buttonSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)

        }

    }
}