package com.angoga.android_nuclearhack.remote.model.response



class PageResponse<T>(
    val content: List<T> = emptyList(),
    val page: Long = -1,
    val size: Long = -1,
    val totalPages: Long = -1,
)