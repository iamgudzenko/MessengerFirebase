package com.example.testmes.View

interface IPhoneAuthView {
    fun verificationSuccess(storedVerificationId:String)
    fun loginPhoneError(message:String)
    fun afterVerification(message:String)
    fun verificationFailed(message:String)
    fun isSignedIn(isSignedIn: Boolean)
}