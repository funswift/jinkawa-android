package com.example.taross.jinkawa_android

/**
 * Created by taross on 2017/11/07.
 */

interface LoadableList{
    fun loadData():MutableList<out Any>
    fun reflesh()
}