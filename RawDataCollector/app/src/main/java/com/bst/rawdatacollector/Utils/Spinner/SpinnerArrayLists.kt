package com.bst.rawdatacollector.Utils.Spinner

class SpinnerArrayLists
{
    private var mWorkerList: ArrayList<String> = ArrayList()
    var workerList: ArrayList<String>
        get()
        {
            return mWorkerList
        }
        set(values)
        {
            mWorkerList = values
        }

    private var mEquipmentList: ArrayList<String> = ArrayList()
    var equipmentList: ArrayList<String>
        get() = mEquipmentList
        set(values)
        {
            mWorkerList = values
        }

    private var mProductList: ArrayList<String> = ArrayList()
    var productList: ArrayList<String>
        get() = mProductList
        set(values)
        {
            mProductList = values
        }

    private var mProcessList: ArrayList<String> = ArrayList()
    var processList: ArrayList<String>
        get() = mProcessList
        set(values)
        {
            mProcessList = values
        }
    private var mToolList:ArrayList<String> = ArrayList()
    var toolList :ArrayList<String>
        get() = mToolList
        set(values)
        {
            mToolList = values
        }

}