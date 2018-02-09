package {{ package.name }}.dto

import play.api.libs.json._
import {{ package.name }}.model.OrderItem

case class PostOrderItem(
    quantity: Long,
    price: Double,
    {{ base_product.name | lower() }}Id: Long,
    shoppingCartId: Option[Long],
    orderId: Option[Long]
)

object PostOrderItem {

  implicit val orderItemReads = Json.reads[PostOrderItem]

  implicit def postOrderItemToOrderItem(postOrderItem: PostOrderItem): OrderItem =
    new OrderItem(
      -1,
      postOrderItem.quantity,
      postOrderItem.price,
      postOrderItem.{{ base_product.name | lower() }}Id,
      postOrderItem.shoppingCartId,
      postOrderItem.orderId
    )
}
