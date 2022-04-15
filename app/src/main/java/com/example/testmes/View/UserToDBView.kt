package com.example.testmes.View

interface UserToDBView {
    fun writeUserDbSuccess(message:String)
    fun writeUserDbError(message:String)
}