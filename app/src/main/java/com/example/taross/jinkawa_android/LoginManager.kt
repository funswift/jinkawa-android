package com.example.taross.jinkawa_android

import com.example.taross.model.Account
import com.nifty.cloud.mb.core.NCMBObject
import com.nifty.cloud.mb.core.NCMBQuery

/**
 * Created by taross on 2017/08/21.
 */

object LoginManager{
    var isLogin:Boolean = false
    var account:Account? = null

    fun login(id:String, password:String):Boolean{
        val query:NCMBQuery<NCMBObject> = NCMBQuery("Accounts")
        query.whereEqualTo("userId", id)
        query.whereEqualTo("password", password)

        val results:List<NCMBObject> = try {
            query.find()
        } catch (e:Exception){
            emptyList<NCMBObject>()
        }

        if (results.isNotEmpty()){
            val result = results[0]

            this.account = Account(
                    result.getString("userId"),
                    result.getString("password"),
                    result.getString("role"),
                    result.getList("auth").toList() as List<String>)
            this.isLogin = true
        }

        return this.isLogin
    }

    fun logout(){
        this.isLogin = false
    }
}