package com.cahya.githubusersapp.data.remote.retrofit

import com.cahya.githubusersapp.data.remote.response.UserResponse
import com.cahya.githubusersapp.data.local.entity.ModelUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<ModelUser>

    @GET("search/users")
    suspend fun getSearchUsers(
        @Query("q") query: String
    ): UserResponse

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): ModelUser

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String
    ): List<ModelUser>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String
    ): List<ModelUser>
}