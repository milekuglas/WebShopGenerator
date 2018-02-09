package {{ package.name }}.dto

import play.api.libs.json._
import {{ package.name }}.model.OrderItem

case class GetOrderItem(
    id: Long,
    quantity: Long,
    price: Double,
    {{ base_product.name | lower() }}Id: Long,
    shoppingCartId: Option[Long],
    orderId: Option[Long]
)

object GetOrderItem {

  implicit val orderItemsWrites = Json.writes[GetOrderItem]

  implicit def orderItemToGetOrderItem(orderItem: OrderItem): GetOrderItem =
    new GetOrderItem(
      orderItem.id,
      orderItem.quantity,
      orderItem.price,
      orderItem.{{ base_product.name | lower() }}Id,
      orderItem.shoppingCartId,
      orderItem.orderId
    )
}
