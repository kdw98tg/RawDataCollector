package com.bst.rawdatacollector.UserData

import android.content.Context

class UserData private constructor(context: Context)
{

    companion object
    {
        @Volatile
        private var instance: UserData? = null

        //싱글톤 패턴
        fun getInstance(context: Context): UserData
        {
            instance ?: synchronized(UserData::class.java) {
                instance ?: UserData(context).also {
                    instance = it
                }
            }
            return instance!!
        }
    }

    private val userAuthority = 0

    fun getUserAuthority(): Int
    {
        return userAuthority
    }


}