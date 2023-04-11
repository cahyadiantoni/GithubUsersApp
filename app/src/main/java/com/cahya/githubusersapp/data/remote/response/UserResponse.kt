package com.cahya.githubusersapp.data.remote.response

import com.google.gson.annotations.SerializedName
import com.cahya.githubusersapp.data.local.entity.ModelUser

data class UserResponse(
    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("items")
    val users: List<ModelUser>
)
