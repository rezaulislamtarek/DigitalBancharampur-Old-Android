package com.diatomicsoft.digitalbancharampur.model.data

data class Auth(
    val auth: Boolean,
    val token: String,
    val message: String,
    var name: String,
    var address: String,
    var phone: String,
    var password: String,
    var id: Int
){
    constructor():this(false, "","","","","","",1)
}
