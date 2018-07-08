package {{ package.name }}.service

import javax.inject.{Inject, Singleton}

import com.google.inject.ImplementedBy
import scala.concurrent.{ExecutionContext, Future}
import {{ package.name }}.dto.{GetOrder, PostOrder}
import {{ package.name }}.repository.{OrderItemRepository, OrderRepository}
import {{ package.name }}.model.OrderItem

@ImplementedBy(classOf[OrderServiceImpl])
trait OrderService {
  def getAll(userId: Long): Future[Seq[GetOrder]]
  def insert(order: PostOrder): Future[GetOrder]
}

@Singleton()
class OrderServiceImpl @Inject()(orderRepository: OrderRepository, orderItemRepository: OrderItemRepository)(
  implicit executionContext: ExecutionContext) extends OrderService {

  def getAll(userId: Long): Future[Seq[GetOrder]] = {
    orderRepository.all(userId).map(_.map(GetOrder.orderToGetOrder))
  }

  def insert(order: PostOrder): Future[GetOrder] = {
    orderRepository.insert(order).map(GetOrder.orderToGetOrder).map { order =>
      orderItemRepository.getShoppingCartItemsByUserId(order.userId).map { orderItems =>
        orderItems.foreach(orderItem => {
          val newOrderItem = OrderItem(orderItem.id, orderItem.quantity, orderItem.price, orderItem.productId, null, Some(order.id))
          orderItemRepository.update(orderItem.id, newOrderItem)
        })
      }
      order
    }
  }

}

