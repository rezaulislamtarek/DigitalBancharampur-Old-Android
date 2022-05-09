package com.diatomicsoft.digitalbancharampur.ui.auth

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diatomicsoft.digitalbancharampur.model.data.Auth
import com.diatomicsoft.digitalbancharampur.model.repository.AuthRepository
import com.diatomicsoft.digitalbancharampur.util.ApiExceptions
import java.io.File
import java.lang.Exception

class AuthViewModel(val repository: AuthRepository) : ViewModel() {
    var authListener: AuthListener? = null
    var auth: Auth = Auth()
    var imageUri = MutableLiveData<Uri>()
    var imageFile = MutableLiveData<File>()
    var authResponse = MutableLiveData<Auth>()


    suspend fun login() {
        if (auth.phone.trim().isNullOrEmpty()) {
            authListener?.onError("Please input valid phone number.")
            return
        }
        if (auth.password.isNullOrEmpty()) {
            authListener?.onError("Enter your password.")
            return
        }
        authListener?.onStarted()
        try {
            authResponse.value = repository.userLogin(auth)
            if (authResponse.value!!.auth) {
                //save auth to share preference
                repository.saveAuth(authResponse.value!!)

                authListener?.onSuccess(authResponse.value!!)
            }
            if (!authResponse.value!!.auth) authListener?.onError(authResponse.value!!.message)
        } catch (e: Exception) {
            authListener?.onError(e.message.toString())
        }
    }

    suspend fun signUp() {
        if (imageFile.value == null) {
            authListener?.onError("Attach a profile picture!")
            return
        }
        if (auth.name.trim().isNullOrEmpty()) {
            authListener?.onError("Enter your name!")
            return
        }
        if (auth.phone.trim().length < 11) {
            authListener?.onError("Input valid phone number!")
            return
        }
        if (auth.password.length < 5) {
            authListener?.onError("Password should be at lest 5 character!")
            return
        }
        if (auth.address.isNullOrEmpty()) {
            authListener?.onError("Address should not be empty!!")
            return
        }
        try {
            authListener?.onStarted()
            val res = repository.userSignUp(auth, imageFile.value!!)
            if (res.auth) authListener?.onSuccess(res)
            if (!res.auth) authListener?.onError(res.message)
        } catch (e: ApiExceptions) {
            authListener?.onError(e.message.toString())
        }
    }
}