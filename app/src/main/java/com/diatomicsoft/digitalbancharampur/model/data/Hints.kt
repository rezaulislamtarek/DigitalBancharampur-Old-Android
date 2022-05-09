package com.diatomicsoft.digitalbancharampur.model.data

data class Hints(
    var title: String,
    var address: String,
    var mobile: String,
    var details: String,
    var image: String
) {
    constructor():this("", "", "", "", "")
}
