package com.diploma.customerapp.model

data class Menu(
    var id: String? = null,
    var menuImageUrl: String? = null,
    var foodCategory: String? = null,
    var foodName: String? = null,
    var foodPrice: Long? = null
)
