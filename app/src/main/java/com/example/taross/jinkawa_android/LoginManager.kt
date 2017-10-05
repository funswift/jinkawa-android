package com.example.taross.jinkawa_android

/**
 * Created by taross on 2017/08/21.
 */

object LoginManager{
    var isLogin:Boolean = false

    fun login(){
        this.isLogin = true
    }

    fun logout(){
        this.isLogin = false
    }
}