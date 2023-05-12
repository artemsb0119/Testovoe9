package com.example.testovoe9

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class TrueActivity : AppCompatActivity() {

    private lateinit var imageViewFon2: ImageView
    private lateinit var textViewRight: TextView
    private lateinit var textViewScore: TextView
    private lateinit var textViewQuestion: TextView
    private lateinit var buttonTrue: AppCompatButton
    private lateinit var buttonFalse: AppCompatButton

    private lateinit var questions: List<Quiz>

    private lateinit var activity: Activity

    private var answeredQuestion = 0
    private val countOfQuestions = 20
    private var countOfRightAnswers = 0
    private var score: String? = null
    private var clicked: Int? = null
    private var getRightAnswerPosition: Int? = null
    var list: MutableList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_true)

        buttonTrue = findViewById(R.id.buttonTrue)
        textViewRight = findViewById(R.id.textViewRight)
        textViewScore = findViewById(R.id.textViewScore)
        textViewQuestion = findViewById(R.id.textViewQuestion)
        buttonFalse = findViewById(R.id.buttonFalse)
        imageViewFon2 = findViewById(R.id.imageViewFon2)

        Glide.with(this)
            .load("http://135.181.248.237/9/fon2.png")
            .into(imageViewFon2)

        score = String.format("%s / %s", answeredQuestion, countOfQuestions)
        textViewScore.text = score
        textViewRight.text = countOfRightAnswers.toString()
        buttonTrue.setOnClickListener {
            clicked = 1
            playGame()
        }
        buttonFalse.setOnClickListener {
            clicked = 2
            playGame()
        }
        activity = this
        loadData()
    }

    private fun loadData() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val data = URL("http://135.181.248.237/9/quiz.json").readText()
                val gson = Gson()
                questions = gson.fromJson(data, Array<Quiz>::class.java).toList()
                activity.runOnUiThread {
                    generateQuestion()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun generateQuestion() {
        var repeat = false
        val a = (Math.random() * questions.size).toInt()
        for (lists in list) {
            if (lists == a) {
                repeat = true
            }
        }
        if (!repeat) {
            list.add(a)
            textViewQuestion.text = questions[a].title
            getRightAnswerPosition = questions[a].answer
        } else {
            generateQuestion()
        }
    }
    private fun playGame() {
        if(buttonTrue.isPressed||buttonFalse.isPressed){
            if (clicked==getRightAnswerPosition) {
                countOfRightAnswers++
                textViewRight.text = countOfRightAnswers.toString()
            }
        }
        answeredQuestion++
        score = String.format("%s / %s", answeredQuestion, countOfQuestions)
        textViewScore.text = score
        if (answeredQuestion == 20){
            gameOver()
        } else {
            generateQuestion()
        }
    }

    private fun gameOver() {
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        var max = sharedPreferences.getInt("max", 0)
        if (countOfRightAnswers >= max) {
            max = countOfRightAnswers
            val editor = getSharedPreferences("my_preferences", Context.MODE_PRIVATE).edit()
            editor.putInt("max", max)
            editor.apply()
        }
        val intent = Intent(this, TrueScoreActivity::class.java)
        intent.putExtra("current", countOfRightAnswers)
        startActivity(intent)
        finish()
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
        return super.onKeyDown(keyCode, event)
    }
}