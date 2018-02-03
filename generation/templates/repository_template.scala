package {{ package.name }}.repository

import slick.jdbc.PostgresProfile.api._
import {{ package.name }}.model.{{ product.name }}
import {{ package.name }}.repository.table.{{ product.name }}Table
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class {{ product.name }}Repository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                                  (implicit executionContext: ExecutionContext) {

  val {{ product.name }}s = TableQuery[{{ product.name }}Table]
  
  val db = dbConfigProvider.get.db

  {% if product.type == "base" %}
  def all(): Future[Seq[{{ product.name }}]] = db.run({{ product.name }}s.result)

  def get(id: Long): Future[Option[{{ product.name }}]] = db.run({{ product.name }}s.filter(_.id === id).result.headOption)
  {% endif %}

  def insert({{ product.name|lower() }}s: Seq[{{ product.name }}]): Future[Unit] =
    db.run({{ product.name }}s ++= {{ product.name|lower() }}s).map(_ => ())

  def create(): Future[Unit] = db.run({{ product.name }}s.schema.create)

  {% if product.type == "base" %}
  def count(): Future[Int] = db.run({{ product.name }}s.length.result)
  {% endif %}
}
