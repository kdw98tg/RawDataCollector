package com.bst.rawdatacollector.DataClass

data class Product(
    var productName: String, var productCode: String, var requestName: String, var acceptName: String, var productImg:String
)
{
    constructor():this("","","","","")
}

