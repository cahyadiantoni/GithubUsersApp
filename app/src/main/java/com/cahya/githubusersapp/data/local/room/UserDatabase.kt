package com.cahya.githubusersapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cahya.githubusersapp.data.local.entity.ModelUser

@Database(
    entities = [ModelUser::class],
    version = 10
)
abstract class UserDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao

    companion object {
        @Volatile
        private var instance: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java, "GithubUsersApp.db"
                ).fallbackToDestructiveMigration().build()
            }
    }
}