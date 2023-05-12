package com.example.testovoe9

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide

class TrueScoreActivity : AppCompatActivity() {

    private lateinit var textViewResult: TextView
    private lateinit var textViewMax: TextView
    private lateinit var buttonRetry: AppCompatButton
    private lateinit var imageViewFon2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_true_score)

        textViewResult = findViewById(R.id.textViewResult)
        textViewMax = findViewById(R.id.textViewMax)
        buttonRetry = findViewById(R.id.buttonRetry)
        imageViewFon2 = findViewById(R.id.imageViewFon2)
        Glide.with(this)
            .load("http://135.181.248.237/9/fon2.png")
            .into(imageViewFon2)
        val result = intent.getIntExtra("current",0)
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val max = sharedPreferences.getInt("max", 0)
        val score = String.format("Your result: %s", result)
        textViewResult.text = score
        val maxResult = String.format("Maximum result: %s", max)
        textViewMax.text = maxResult
        textViewResult.text = score

        buttonRetry.setOnClickListener {
            val intent = Intent(this, TrueActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
        return super.onKeyDown(keyCode, event)
    }
}