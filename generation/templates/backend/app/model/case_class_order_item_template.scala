package {{ package.name }}.model

case class OrderItem(id: Long,
                     quantity: Long,
                     price: Double,
                     {{ base_product.name | lower() }}Id: Long,
                     shoppingCartId: Option[Long],
                     orderId: Option[Long])
