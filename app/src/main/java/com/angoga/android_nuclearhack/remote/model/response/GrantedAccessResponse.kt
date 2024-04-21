package com.angoga.android_nuclearhack.remote.model.response


data class GrantedAccessResponse(
    val accessJwt: String,
    val privateKey: String,
)
