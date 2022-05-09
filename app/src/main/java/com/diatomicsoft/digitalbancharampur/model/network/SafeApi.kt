package com.diatomicsoft.digitalbancharampur.model.network

import android.content.Context
import com.diatomicsoft.digitalbancharampur.model.sharepref.Prefs
import com.diatomicsoft.digitalbancharampur.util.ApiExceptions
import retrofit2.Response

abstract class SafeApi(context: Context): Prefs(context) {
    suspend fun<T: Any> apiRequest(call: suspend ()-> Response<T>) : T{
        val response = call.invoke()
        if(response.isSuccessful) return response.body()!!
        else throw ApiExceptions(response.message()+"\nError Code "+response.code())
    }
    suspend fun<T: Any> apiRequestWithList(call: suspend () -> Response<List<T>>) : List<T> {
        val response = call.invoke()
        if(response.isSuccessful ) return response.body()!!
        else throw ApiExceptions(response.message()+"\nError Code "+response.code())
    }
}