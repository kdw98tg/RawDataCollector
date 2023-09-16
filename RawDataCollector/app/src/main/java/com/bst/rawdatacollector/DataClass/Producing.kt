package com.bst.rawdatacollector.DataClass

data class Producing(
    var produceNumber:String,
    var productName: String,
    var productCode: String,
    var requestName: String,
    var acceptName: String,
    var productImg: String,
    var equipmentCode: String,
    var process:String
)
{
    constructor() : this("","", "", "", "", "", "","")
}

