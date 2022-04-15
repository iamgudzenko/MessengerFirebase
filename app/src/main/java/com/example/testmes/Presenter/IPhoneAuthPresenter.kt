package com.example.testmes.Presenter

interface IPhoneAuthPresenter {
    fun loginPhone(number:String)
    fun sendVerificationCode(number: String)
    fun isSignedIn()
}