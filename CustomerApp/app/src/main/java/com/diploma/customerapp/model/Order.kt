package com.diploma.customerapp.model
data class Order(
    var id: String? = null,
    var userNumber: String? = null,
    var roomNumber: String? = null,
    var time: String? = null,
    var date: String? = null,
    var details: String? = null,
    var visibility: Boolean = false
)
