package com.bst.rawdatacollector.DataClass

data class ProductError(
    private var errorName:String,
    private var errorAmount:Int,
)
{
constructor():this("",0)
}
