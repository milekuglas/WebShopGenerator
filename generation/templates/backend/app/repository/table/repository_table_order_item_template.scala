package {{ package.name }}.repository.table

import {{ package.name }}.model.OrderItem
import slick.jdbc.PostgresProfile.api._

class OrderItemTable(tag: Tag) extends Table[OrderItem](tag, "ORDERITEMS") {

  val {{ base_product.name }}s      = TableQuery[{{ base_product.name }}Table]
  val ShoppingCarts = TableQuery[ShoppingCartTable]
  val Orders        = TableQuery[OrderTable]

  def id             = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def quantity       = column[Long]("quantity")
  def price          = column[Double]("price")
  def {{ base_product.name |lower() }}Id      = column[Long]("{{ base_product.name | lower() }}_id")
  def shoppingCartId = column[Option[Long]]("shopping_cart_id")
  def orderId        = column[Option[Long]]("order_id")

  def {{ base_product.name |lower() }}      = foreignKey("{{ base_product.name |lower() }}_FK", {{ base_product.name |lower() }}Id, {{ base_product.name }}s)(_.id)
  def shoppingCart = foreignKey("shopping_cart_FK", shoppingCartId, ShoppingCarts)(_.id)
  def order        = foreignKey("order_FK", orderId, Orders)(_.id)

  def * =
    (id, quantity, price, {{ base_product.name |lower() }}Id, shoppingCartId, orderId) <> (OrderItem.tupled, OrderItem.unapply)
}

object OrderItemTable {

  lazy val OrderItems = TableQuery[OrderItemTable]

}
