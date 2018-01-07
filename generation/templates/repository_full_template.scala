package {{ package.name }}.repository

import slick.jdbc.PostgresProfile.api._
import model.{ {{ product.name }}, {{product.name}}Full }
import javax.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}


@Singleton()
class {{ product.name }}FullRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                                    (implicit executionContext: ExecutionContext) extends {{ product.name }}Component
  with HasDatabaseConfigProvider[JdbcProfile] {

  private val {{ product.name }}s = TableQuery[{{ product.name }}Table]
  private val {{ base_product.name }}s = TableQuery[{{ base_product.name }}Table]

  def all(): Future[Seq[{{ product.name }}Full]] = db.run({
    for {
      ({{ product.name|lower() }}, {{ base_product.name|lower() }}) <- {{ product.name }}s join {{ base_product.name }}s on (_.
      {{- base_product.name|lower() }}Id === _.id)
    } yield ({{ product.name|lower() }}, {{ base_product.name|lower() }})
  }.result.map(_ map {
    case ({{ product.name|lower() }}: {{ product.name }}, {{ base_product.name|lower() }}:{{ base_product.name}}) =>
    {{- product.name }}Full({{ product.name|lower() }}, {{ base_product.name|lower() }})
  })
  )

  def get(id: Long):Future[Option[{{ product.name }}Full]] = db.run({
    for {
      ({{ product.name|lower() }}, {{ base_product.name|lower() }}) <-
      {{- product.name }}s.filter(_.{{ base_product.name|lower()}}Id === id) join {{ base_product.name}}s on (_.
      {{- base_product.name|lower()}}Id === _.id)
    } yield ({{ product.name|lower() }}, {{ base_product.name|lower() }})
  }.result.head map {
    case ({{ product.name|lower() }}: {{ product.name }},
    {{- base_product.name|lower() }}:{{ base_product.name }}) => Some(
    {{- product.name }}Full({{ product.name|lower() }}, {{ base_product.name|lower() }}))
    case _ => None
  })

  def insert({{ product.name|lower() }}Full: {{ product.name }}Full): Future[{{ product.name }}Full] = db.run((for {
    {{ base_product.name|lower() }} <- ({{ base_product.name }}s returning {{ base_product.name }}s) += {{ product.name|lower() }}Full.
    {{- base_product.name|lower() }}
    {{ product.name|lower() }} <- ({{ product.name }}s returning {{ product.name }}s) += {{ product.name|lower() }}Full.
    {{- product.name|lower() }}.copy({{ base_product.name|lower() }}Id = {{ base_product.name|lower() }}.id)
  } yield {{ product.name }}Full({{ product.name|lower() }}, {{ base_product.name|lower() }})).transactionally)

  def delete(id: Long): Future[Int] = db.run((for {
    rows{{ product.name }} <- {{ product.name }}s.filter(_.{{ base_product.name|lower() }}Id === id).delete if rows{{ product.name }} == 1
    rows{{ base_product.name }} <- {{ base_product.name }}s.filter(_.id === id).delete if rows{{ base_product.name }} == 1
  } yield rows{{ base_product.name }}).transactionally)

  def update(id: Long, {{ product.name|lower() }}Full: {{ product.name }}Full): Future[Int] = db.run((for {
    rows{{ product.name }} <- {{ product.name }}s.filter(_.
    {{- base_product.name|lower() }}Id === id).update({{ product.name|lower() }}Full.
    {{- product.name|lower() }}.copy(id)) if rows{{ product.name }} == 1
    rows{{ base_product.name }} <- {{ base_product.name }}s.filter(_.id === id).update(
    {{- product.name|lower() }}Full.{{ base_product.name|lower() }}.copy(id)) if rows{{ base_product.name }} == 1
  } yield rows{{ base_product.name }}).transactionally)
}