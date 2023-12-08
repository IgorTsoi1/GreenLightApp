package com.example.greenlight.model

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("question")var question: String? = null,
    @SerializedName("test")var test: String? = null,
    @SerializedName("_id")var questionId: String? = null,
)




