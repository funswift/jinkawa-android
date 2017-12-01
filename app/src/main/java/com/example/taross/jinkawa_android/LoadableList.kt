package com.example.taross.jinkawa_android

/**
 * Created by taross on 2017/11/07.
 */

interface LoadableList<T>{
    fun loadData():MutableList<out Any>
    fun reflesh()
    fun filter()
    var items:MutableList<T>
}