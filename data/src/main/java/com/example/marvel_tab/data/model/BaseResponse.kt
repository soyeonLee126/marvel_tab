package com.example.marvel_tab.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("code") val code: Int,
    @SerialName("data") val data: T
)
