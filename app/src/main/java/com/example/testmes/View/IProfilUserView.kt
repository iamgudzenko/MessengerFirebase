package com.example.testmes.View

import com.example.testmes.Model.Users


interface IProfilUserView {
    fun exitUser()
    fun getProfilUserSuccess(userCurrent: Users?)
    fun getProfilUserError(message:String)
}