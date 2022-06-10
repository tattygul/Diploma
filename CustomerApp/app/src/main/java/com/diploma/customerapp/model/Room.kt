package com.diploma.customerapp.model

data class Room(
    var id: String? = null,
    var roomImageUri: String? = null,
    var roomNumber: String? = null,
    var roomCapacity: Long? = null,
    var roomPrice: Long? = null
)
