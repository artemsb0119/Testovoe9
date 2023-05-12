package com.example.testovoe9

import com.google.gson.annotations.SerializedName

data class Quiz (

    @SerializedName("title") val title : String,
    @SerializedName("answer") val answer : Int
)
