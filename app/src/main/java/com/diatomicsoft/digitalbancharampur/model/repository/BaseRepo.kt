package com.diatomicsoft.digitalbancharampur.model.repository

import android.content.Context
import com.diatomicsoft.digitalbancharampur.model.data.User
import com.diatomicsoft.digitalbancharampur.model.network.Api
import com.diatomicsoft.digitalbancharampur.model.network.SafeApi
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

abstract class BaseRepo(contex: Context, private val apiInstance: Api) : SafeApi(contex) {
    suspend fun getUserById( id: Int): User {
        return apiRequest { apiInstance.getUser(id) }
    }

    fun makeStringPart(text: String): RequestBody {
        return RequestBody.create(MultipartBody.FORM, text)
    }

    fun makeFilePart(file: File, part: String): MultipartBody.Part {
        val requestFile: RequestBody =
            RequestBody.create(
                MediaType.parse("image/*"),
                file
            )
        return MultipartBody
            .Part
            .createFormData(part, file.name, requestFile)
    }


}