package com.angoga.android_nuclearhack.remote.model.response

class WebAuthLoginResponse(
    val sessionId: String,
    val challenge: String,
    val checkJwt : String
)