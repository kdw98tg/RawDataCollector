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

        private var userCode: String = ""
        private var userPw: String = ""
        private var userName: String = ""
        private var userEmail: String = ""
        private var userCompany: String = ""
        private var userPhoneNumber: String = ""
        private var userProfileImg:String=""
        private var userPosition: String = ""

    }
//region getter/setter
     fun getUserCode(): String
    {
        return userCode
    }

     fun setUserCode(_userCode: String)
    {
        userCode = _userCode
    }

     fun getUserPw(): String
    {
        return userPw
    }

     fun setUserPw(_userPw: String)
    {
        userPw = _userPw
    }

     fun getUserName(): String
    {
        return userName
    }

     fun setUserName(_userName: String)
    {
        userName = _userName
    }

     fun getUserEmail(): String
    {
        return userCode
    }

     fun setUserEmail(_userEmail: String)
    {
        userEmail = _userEmail
    }

     fun getUserCompany(): String
    {
        return userCompany
    }

     fun setUserCompany(_userCompany: String)
    {
        userCompany = _userCompany
    }

     fun getUserPhoneNumber(): String
    {
        return userPhoneNumber
    }

     fun setUserPhoneNumber(_userPhoneNumber: String)
    {
        userPhoneNumber = _userPhoneNumber
    }
     fun getUserPosition(): String
    {
        return userPosition
    }

     fun setUserPosition(_userPosition: String)
    {
        userPosition = _userPosition
    }

    fun getUserProfileImg(): String
    {
        return userProfileImg
    }

    fun setUserProfileImg(_userProfileImg: String)
    {
        userProfileImg = _userProfileImg
    }

//endregion

}