package com.bst.rawdatacollector.DataClass

data class Equipment(
    var equipmentCode:String,
    var equipmentImage:String,
    var equipmentName:String
)
{
    constructor():this("","","")
}