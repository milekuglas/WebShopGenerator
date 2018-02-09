package {{ package.name }}.dto

import play.api.libs.json._
import {{ package.name }}.model.Order

case class PostOrder(
                          totalPrice: Double,
                          userId: Long
                        )

object PostOrder {

  implicit val orderReads = Json.reads[PostOrder]

  implicit def postOrderToOrder(postOrder: PostOrder): Order =
    new Order(
      -1,
      postOrder.totalPrice,
      postOrder.userId
    )
}

