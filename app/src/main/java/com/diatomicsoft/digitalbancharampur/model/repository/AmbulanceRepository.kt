package com.diatomicsoft.digitalbancharampur.model.repository

import android.content.Context
import android.util.Log
import com.diatomicsoft.digitalbancharampur.model.data.Ambulance
import com.diatomicsoft.digitalbancharampur.model.data.CommonResponse
import com.diatomicsoft.digitalbancharampur.model.network.Api
import java.io.File

class AmbulanceRepository(val api: Api,val context: Context) : BaseRepo(context,api) {
    suspend fun getAllAmbulance(): List<Ambulance> {
        return apiRequestWithList { api.getAllAmbulance() }
    }
    suspend fun addaAmbulance(amb : Ambulance, file : File): CommonResponse{
        val driverName = makeStringPart(amb.driverName)
        val address = makeStringPart(amb.address)
        val contributorId = makeStringPart(amb.contributorId.toString())
        val ambulanceRegNo = makeStringPart(amb.ambulanceRegNo)
        val others = makeStringPart(amb.others)
        val mobile = makeStringPart(amb.mobile)
        val image = makeFilePart(file,"image")
        print("Call from AmbulanceRepo")
        return apiRequest { api.addAmbulance(driverName,address,contributorId,ambulanceRegNo,others, mobile,image) }
    }
    fun print(msg :String){
        Log.d("ambulance", msg)
    }
}