package com.diatomicsoft.digitalbancharampur.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    var driverName: String,
    var ownerName: String,
    var ownerNumber: String,
    var driverNumber: String,
    var address: String,
    var contributorId: Int,
    var carRegNo: String,
    var others: String,
    var carRoute: String,
    var startingTime: String,
    var carModel: String,
    var image: String
) : Parcelable {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        0,
        "",
        "",
        "",
        "",
        "",
        ""
    )
}
