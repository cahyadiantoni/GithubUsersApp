package com.cahya.githubusersapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cahya.githubusersapp.data.local.entity.ModelUser

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getUsers(): LiveData<List<ModelUser>>

    @Query("SELECT * FROM users WHERE login = :username")
    fun getUser(username: String): LiveData<ModelUser>

    @Query("SELECT * FROM users WHERE favorite = 1")
    fun getFavoriteUsers(): LiveData<List<ModelUser>>

    @Query("SELECT EXISTS(SELECT * FROM users WHERE login = :username AND favorite = 1)")
    suspend fun isFavoriteUser(username: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUsers(users: List<ModelUser>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUser(user: ModelUser)

    @Query("UPDATE users SET favorite = :favorite WHERE login = :username")
    suspend fun setUserFavorite(username: String, favorite: Boolean)
}