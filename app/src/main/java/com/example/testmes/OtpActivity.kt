package com.example.testmes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.testmes.Presenter.IOtpPresenter
import com.example.testmes.Presenter.OtpPresenter
import com.example.testmes.View.IOtpView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OtpActivity : AppCompatActivity(), IOtpView {
    lateinit var otp: EditText
    lateinit var textInfo: TextView
    lateinit var auth: FirebaseAuth
    lateinit var otpPresenter: IOtpPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        auth=FirebaseAuth.getInstance()
        otpPresenter = OtpPresenter(auth, this, this)
        otp = findViewById(R.id.editOtp)

        textInfo = findViewById(R.id.textInfoOtp)

        val storedVerificationId = intent.getSerializableExtra("storedVerificationId")

        findViewById<Button>(R.id.butOtpExam).setOnClickListener {
            val smsCode = otp.getText().toString()
            if(smsCode.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), smsCode)

                otpPresenter.signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(this,"Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun signInWithCredentialSuccess(message: String, isNewUser:Boolean) {
        if(isNewUser){
            val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this , ChatsActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun signInWithCredentialError(message: String) {
        textInfo.setText(message)
    }
}