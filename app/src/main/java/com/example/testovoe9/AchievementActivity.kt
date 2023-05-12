package com.example.testovoe9

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class AchievementActivity : AppCompatActivity() {

    private lateinit var facts: List<Facts>
    private lateinit var activity: Activity
    private lateinit var imageViewFon2: ImageView

    private lateinit var viewpager: ViewPager2
    private lateinit var pravo: ImageView
    private lateinit var levo: ImageView
    private lateinit var adapter: FactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts)

        levo = findViewById(R.id.levo)
        pravo = findViewById(R.id.pravo)
        viewpager = findViewById(R.id.viewpager)
        imageViewFon2 = findViewById(R.id.imageViewFon2)
        activity = this

        Glide.with(this)
            .load("http://135.181.248.237/9/fon2.png")
            .into(imageViewFon2)

        loadData()
    }

    private fun loadData() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val data = URL("http://135.181.248.237/9/achievements.json").readText()
                val gson = Gson()
                facts = gson.fromJson(data, Array<Facts>::class.java).toList()

                activity.runOnUiThread {
                    adapter = FactsAdapter(facts)
                    viewpager.adapter = adapter
                    viewpager.clipToPadding = false
                    viewpager.clipChildren = false
                    viewpager.isUserInputEnabled = false
                    viewpager.offscreenPageLimit = 2
                    pravo.setOnClickListener {
                        var currentItemIndex = viewpager.currentItem
                        if (currentItemIndex == facts.size - 2) {
                            pravo.visibility = View.INVISIBLE
                            currentItemIndex++
                        } else if (currentItemIndex > facts.size - 2) {
                            pravo.visibility = View.INVISIBLE
                        } else {
                            pravo.visibility = View.VISIBLE
                            currentItemIndex++
                        }
                        if (currentItemIndex > 0) {
                            levo.visibility = View.VISIBLE
                        } else {
                            levo.visibility = View.INVISIBLE
                        }
                        viewpager.setCurrentItem(currentItemIndex, true)
                    }
                }

                levo.setOnClickListener {
                    var currentItemIndex = viewpager.currentItem
                    if (currentItemIndex > 1) {
                        levo.visibility = View.VISIBLE
                        currentItemIndex--
                    } else if (currentItemIndex == 1) {
                        levo.visibility = View.INVISIBLE
                        currentItemIndex--
                    } else{
                        levo.visibility = View.INVISIBLE
                    }

                    if (currentItemIndex == facts.size - 1) {
                        pravo.visibility = View.INVISIBLE
                    } else {
                        pravo.visibility = View.VISIBLE
                    }
                    viewpager.setCurrentItem(currentItemIndex, true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}