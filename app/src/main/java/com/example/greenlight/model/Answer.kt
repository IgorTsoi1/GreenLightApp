package com.example.greenlight.model

import com.google.gson.annotations.SerializedName

data class Answer(
    @SerializedName("answer")var answer: String? = null,
    @SerializedName("question")var question: String? = null,
    @SerializedName("points")var points: String? = null,
    @SerializedName("_id")var answerId: String? = null,
)




