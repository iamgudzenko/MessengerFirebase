package com.example.testmes.View

interface IOtpView {
    fun signInWithCredentialSuccess(message:String, isNewUser:Boolean)
    fun signInWithCredentialError(message:String)
}