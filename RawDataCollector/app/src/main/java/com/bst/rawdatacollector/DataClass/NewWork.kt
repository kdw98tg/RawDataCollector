package com.bst.rawdatacollector.DataClass

data class NewWork(
    var requestUser:String,
    var acceptUser:String,
    var workDate:String,
    var equipment:String,
    var product:String,
    var toolCode:String,
    var amount:String,
    var process:String
)
{
    constructor():this("","","","","","","","")
}

