package com.bst.rawdatacollector.DataClass

data class Tool(
     var toolCode:String,
     var toolName:String,
     var toolImg:String,
     var usesAmount:Int
)
{
     constructor():this("","","",0)
}
