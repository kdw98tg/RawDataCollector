package com.bst.rawdatacollector.DataClass

data class Producing(
    var productName: String,
    var productCode: String,
    var requestName: String,
    var acceptName: String,
    var productImg: String,
    var equipmentCode: String
)
{
    constructor() : this("", "", "", "", "", "")
}

