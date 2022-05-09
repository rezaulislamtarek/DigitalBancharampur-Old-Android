package com.diatomicsoft.digitalbancharampur.model.repository

import android.content.Context
import android.util.Log
import com.diatomicsoft.digitalbancharampur.model.data.CommonResponse
import com.diatomicsoft.digitalbancharampur.model.data.Places
import com.diatomicsoft.digitalbancharampur.model.network.Api
import java.io.File

class PlaceRepository(private val api: Api, val context: Context) : BaseRepo(context,api) {
    suspend fun getPlacesByType(type: String): List<Places> {
        return apiRequestWithList { api.getPlacesByType(type) }
    }

    suspend fun addPlace(places: Places, image: File): CommonResponse {
        val title = makeStringPart(places.title)
        val type = makeStringPart(places.type)
        val address = makeStringPart(places.address)
        val mobile = makeStringPart(places.mobile)
        val details = makeStringPart(places.details)
        val contributorId = makeStringPart(places.contributorId.toString())
        val image = makeFilePart(image, "image")

        Log.d("repo","Add place"+places.title)

        return apiRequest { api.addPlace(title,type,mobile, details, address, contributorId, image) }
    }
}