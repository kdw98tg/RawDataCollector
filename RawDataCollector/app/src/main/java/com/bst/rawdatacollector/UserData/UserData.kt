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

    private var mUserCode: String = ""
    var userCode: String
        get() = mUserCode
        set(values)
        {
            mUserCode = values
        }

    private var mUserPw: String = ""
    var userPw: String
        get() = mUserPw
        set(values)
        {
            mUserPw = values
        }

    private var mUserName: String = ""
    var userName: String
        get() = mUserName
        set(values)
        {
            mUserName = values
        }

    private var mUserEmail: String = ""
    var userEmail: String
        get() = mUserEmail
        set(values)
        {
            mUserEmail = values
        }

    private var mUserCompany: String = ""
    var userCompany: String
        get() = mUserCompany
        set(values)
        {
            mUserCompany = values
        }

    private var mUserPhoneNumber: String = ""
    var userPhoneNumber: String
        get() = mUserPhoneNumber
        set(values)
        {
            mUserPhoneNumber = values
        }

    private var mUserProfileImg: String = ""
    var userProfileImg: String
        get() = mUserProfileImg
        set(values)
        {
            mUserProfileImg = values
        }

    private var mUserPosition: String = ""
    var userPosition: String
        get() = mUserPosition
        set(values)
        {
            mUserPosition = values
        }

    private var mStartWork: Boolean = false
    var startWork: Boolean
        get() = mStartWork
        set(values)
        {
            mStartWork = values
        }


}