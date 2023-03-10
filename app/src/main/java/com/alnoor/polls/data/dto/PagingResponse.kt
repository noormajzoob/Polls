package com.alnoor.polls.data.dto

import com.google.gson.annotations.SerializedName

data class PagingResponse<T>(
    val offset: Long,
    val limit: Int,
    @SerializedName("next_offset")
    val next: Long,
    @SerializedName("page_size")
    val pageSize: Int,
    val data: List<T>
)
