package com.example.mytest.model.response.token

data class GetToken(val access_token: String,
                    val token_type: String,
                    val is_verified: Boolean)
