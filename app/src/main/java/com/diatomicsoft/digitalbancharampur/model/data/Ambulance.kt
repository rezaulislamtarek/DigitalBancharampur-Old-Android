package com.diatomicsoft.digitalbancharampur.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ambulance(
    var driverName: String,
    var address: String,
    var contributorId: Int,
    var ambulanceRegNo: String,
    var others: String,
    var mobile : String,
    var image: String
) : Parcelable {
    constructor():this("","",1,"","","","")
}
