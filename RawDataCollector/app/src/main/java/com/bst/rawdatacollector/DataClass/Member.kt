package com.bst.rawdatacollector.DataClass

data class Member(
    var memberCode:String,
    var memberName:String,
    var memberEmail:String,
    var memberPhoneNumber:String,
    var memberProfileImg:String,
    var memberPosition:String,
    var memberWage:String
)
{
    constructor():this("","","","","","","")
}
