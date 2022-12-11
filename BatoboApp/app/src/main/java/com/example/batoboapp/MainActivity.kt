package com.example.batoboapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.batoboapp.databinding.ActivityMainBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.batoboapp.ui.main.ListKelompokActivity
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private var valid_input: Boolean = false

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnSendOTP.setOnClickListener { view ->
            if (view.id == R.id.btnSendOTP) {
                startPhoneNumberVerification(binding.etInputNomor.text.toString())
            }
        }

        binding.btnResendOTP.setOnClickListener { view ->
            if (view.id == R.id.btnResendOTP) {
                resendVerificationCode(binding.etInputNomor.text.toString(), resendToken)
            }
        }

        binding.btnMasukKelompok.setOnClickListener { view ->
            if (view.id == R.id.btnMasukKelompok) {
                verifyPhoneNumberWithCode(
                    binding.etInputNomor.text.toString(),
                    binding.etInputOTP.text.toString()
                )
                if (valid_input == true) {
                    val intent = Intent(this, ListKelompokActivity::class.java)
                    startActivity(intent)
                }

                //---**---
                // startPhoneNumberVerification(phoneNumber: String) V
                // verifyPhoneNumberWithCode(verificationId: String?, code: String)
                // resendVerificationCode(phoneNumber: String,token: PhoneAuthProvider.ForceResendingToken?) V
                // updateUI(user: FirebaseUser? = mAuth.currentUser)

            }
        }
        binding.btnMasukPelanggan.setOnClickListener { view ->
            if (view.id == R.id.btnMasukPelanggan) {
                verifyPhoneNumberWithCode(
                    binding.etInputNomor.text.toString(),
                    binding.etInputOTP.text.toString()
                )
                if (valid_input == true) {
                    val intent = Intent(this, ListKelompokActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        mAuth = Firebase.auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Log.e(TAG, "Invalid Credentials")
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Log.e(TAG, "Too Many Requests")
                }
            }
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d(TAG, "onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }


    private fun startPhoneNumberVerification(phoneNumber: String) {
        try {
            val options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        } catch (error: Exception){
            Log.d(TAG, "ERRORNYA: " + error.toString())
        }
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        valid_input = true
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
        if (token != null) {
            optionsBuilder.setForceResendingToken(token)
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
    }

    private fun updateUI(user: FirebaseUser? = mAuth.currentUser) {

    }

    companion object {
        private const val TAG = "MainActivity"
    }
}