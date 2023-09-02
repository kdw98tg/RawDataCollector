package com.bst.rawdatacollector.Delegate

interface ProductClickListener
{
    fun productSelected(productName:String, productCode:String, requestName:String, acceptName:String, equipmentCode:String)
}