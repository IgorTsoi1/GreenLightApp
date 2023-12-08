package com.example.greenlight.model

import com.google.gson.annotations.SerializedName

data class Test(
    @SerializedName("name")var testName: String? = null,
    @SerializedName("_id")var testId: String? = null,
)




