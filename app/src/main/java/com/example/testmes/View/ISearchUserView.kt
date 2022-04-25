package com.example.testmes.View

import com.example.testmes.Model.Users

interface ISearchUserView {
    fun searchUserSuccess(user: Users?)
    fun searchUserError(message: String)
}