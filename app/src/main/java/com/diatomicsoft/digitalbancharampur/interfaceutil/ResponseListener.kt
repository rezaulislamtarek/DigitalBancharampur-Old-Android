package com.diatomicsoft.digitalbancharampur.interfaceutil

import com.diatomicsoft.digitalbancharampur.model.data.CommonResponse

interface ResponseListener {
    fun onStarted()
    fun onError(message: String)
    fun onSuccess(response: CommonResponse)
}