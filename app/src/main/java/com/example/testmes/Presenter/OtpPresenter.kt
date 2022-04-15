package com.example.testmes.Presenter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.testmes.View.IOtpView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.FirebaseUser




class OtpPresenter(val mAuth:FirebaseAuth, val otpView:IOtpView, val activity:Activity):IOtpPresenter {

    override fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                val user = task.result.additionalUserInfo?.isNewUser
                if (task.isSuccessful) {
                    Log.w("ISNEWUSER", "$user")
                    otpView.signInWithCredentialSuccess("signInWithPhoneAuthCredentialSuccess", user!!)
                } else {

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        otpView.signInWithCredentialError(task.exception.toString())
                    }
                }
            }
    }


}