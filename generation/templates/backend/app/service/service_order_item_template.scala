package {{ package.name }}.service

import javax.inject.{Inject, Singleton}

import com.google.inject.ImplementedBy
import scala.concurrent.{ExecutionContext, Future}
import {{ package.name }}.dto.{GetOrderItem, PostOrderItem}
import {{ package.name }}.repository.OrderItemRepository
import {{ package.name }}.model.ShoppingCart

@ImplementedBy(classOf[OrderItemServiceImpl])
trait OrderItemService {
  def getAll(page: Int, size: Int): Future[Seq[Seq[GetOrderItem]]]
  def getOrderItemsByOrderId(orderId: Long): Future[Seq[GetOrderItem]]
  def getShoppingCartItemsByUserId(userId: Long): Future[Seq[GetOrderItem]]
  def getShoppingCartByUserId(userId: Long): Future[ShoppingCart]
  def getByUserId(id: Long, page: Int, size: Int): Future[Seq[Seq[GetOrderItem]]]
  def insert(orderItem: PostOrderItem): Future[GetOrderItem]
  def update(id: Long, orderItem: PostOrderItem): Future[Int]
  def delete(id: Long): Future[Int]
}

@Singleton()
class OrderItemServiceImpl @Inject()(orderItemRepository: OrderItemRepository)(
  implicit executionContext: ExecutionContext) extends OrderItemService {

  def getAll(page: Int, size: Int): Future[Seq[Seq[GetOrderItem]]] = {
    orderItemRepository.all(page, size).map(_.map(_.map(GetOrderItem.orderItemToGetOrderItem)))
  }

  def getOrderItemsByOrderId(orderId: Long): Future[Seq[GetOrderItem]] = {
    orderItemRepository.getByOrderId(orderId).map(_.map(GetOrderItem.orderItemToGetOrderItem))
  }

  def getShoppingCartItemsByUserId(userId: Long): Future[Seq[GetOrderItem]] = {
    orderItemRepository.getShoppingCartItemsByUserId(userId).map(_.map(GetOrderItem.orderItemToGetOrderItem))
  }

  def getShoppingCartByUserId(userId: Long): Future[ShoppingCart] = {
    orderItemRepository.getShoppingCartByUserId(userId)
  }

  def getByUserId(id: Long, page: Int, size: Int): Future[Seq[Seq[GetOrderItem]]] = {
    orderItemRepository.getByUserId(id, page, size).map(_.map(_.map(GetOrderItem.orderItemToGetOrderItem)))
  }

  def insert(orderItem: PostOrderItem): Future[GetOrderItem] = {
    orderItemRepository.insert(orderItem).map(GetOrderItem.orderItemToGetOrderItem)
  }

  def update(id: Long, orderItem: PostOrderItem): Future[Int] = {
    orderItemRepository.update(id, orderItem)
  }

  def delete(id: Long): Future[Int] = {
    orderItemRepository.delete(id)
  }
}

