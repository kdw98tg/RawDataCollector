package com.bst.rawdatacollector.DataClass

data class ProductError(
     var errorName:String,
     var errorAmount:Int,
)
{
constructor():this("",0)
}
