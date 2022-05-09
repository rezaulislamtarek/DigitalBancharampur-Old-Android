package com.diatomicsoft.digitalbancharampur.model.repository

import android.content.Context
import com.diatomicsoft.digitalbancharampur.model.data.Auth
import com.diatomicsoft.digitalbancharampur.model.data.User
import com.diatomicsoft.digitalbancharampur.model.network.Api
import java.io.File

class AuthRepository(private val api: Api, val context: Context) : BaseRepo(context,api) {
    suspend fun userLogin(auth: Auth): Auth{
        return apiRequest { api.userLogin(auth.phone, auth.password) }
    }

    suspend fun userSignUp(auth: Auth, imageFile: File): Auth {
        val name = makeStringPart(auth.name)
        val phone = makeStringPart(auth.phone)
        val password = makeStringPart(auth.password)
        val address = makeStringPart(auth.address)
        val image = makeFilePart(imageFile,"image")
        return apiRequest { api.signUp(name, phone,password,address,image) }
    }



}