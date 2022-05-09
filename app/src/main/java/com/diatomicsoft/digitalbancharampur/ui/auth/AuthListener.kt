package com.diatomicsoft.digitalbancharampur.ui.auth

import com.diatomicsoft.digitalbancharampur.model.data.Auth

interface AuthListener {
    fun onStarted()
    fun onError(error: String)
    fun onSuccess(auth: Auth)
}