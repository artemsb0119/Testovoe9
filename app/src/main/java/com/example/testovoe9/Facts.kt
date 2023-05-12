package com.example.testovoe9

import com.google.gson.annotations.SerializedName

data class Facts (

    @SerializedName("title") val title : String,
    @SerializedName("image") val image : String
)
