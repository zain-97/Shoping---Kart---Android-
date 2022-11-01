package com.assignment.one.model

import java.io.Serializable

class Cart: Serializable {

    var id: Int = 0

    var productId: Int = 0

    var quantity: Int = 0

    var product: Product = Product()

}