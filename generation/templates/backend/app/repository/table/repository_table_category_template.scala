package {{ package.name }}.repository.table


import {{ package.name }}.model.Category
import slick.jdbc.PostgresProfile.api._

class CategoryTable(tag: Tag)
  extends Table[Category](tag, "CATEGORIES") {

  val Categories = TableQuery[CategoryTable]

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def superCategoryId = column[Option[Long]]("super_category_id")

  def superCategory = foreignKey("super_category_FK", superCategoryId, Categories)(_.id)

  def * = (id, name, superCategoryId) <> (Category.tupled, Category.unapply)
}

object CategoryTable {

  lazy val Categories = TableQuery[CategoryTable]

}