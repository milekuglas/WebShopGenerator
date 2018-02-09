package {{ package.name }}.service

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import {{ package.name }}.dto.{GetOrder, PostOrder}
import {{ package.name }}.repository.OrderRepository


@Singleton()
class OrderService @Inject()(orderRepository: OrderRepository)(
  implicit executionContext: ExecutionContext) {

  def getAll: Future[Seq[GetOrder]] = {
    orderRepository.all().map(_.map(GetOrder.orderToGetOrder))
  }

  def insert(order: PostOrder): Future[GetOrder] = {
    orderRepository.insert(order).map(GetOrder.orderToGetOrder)
  }

}

