package com.bst.rawdatacollector.Utils.Utils.SharedPreferences

class SharedPreferencesProperties {

    companion object {

        // region LoginActivity
        private const val mAutoLogin = "autoLogin"
        //endregion

        //region AttendanceFragment
        private const val mIsAttendance = "isAttendance"
        private const val mAttendanceComplete = "isAttendanceComplete"
        //endregion


        //region properties
        val autoLogin: String
            get() = mAutoLogin

        val isAttendance: String
            get() = mIsAttendance

        val attendanceComplete: String
            get() = mAttendanceComplete

        //endregion
    }


}