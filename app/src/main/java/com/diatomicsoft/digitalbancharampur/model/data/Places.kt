package com.diatomicsoft.digitalbancharampur.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Places(
    var title: String,
    var mobile: String,
    var address: String,
    var latLon: String,
    var contributorId: Int,
    var type: String,
    var details: String,
    var image: String
) : Parcelable {
    constructor():this("","","","",1,"","","")
}
