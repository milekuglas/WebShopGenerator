package {{ package.name }}.repository

import slick.jdbc.PostgresProfile.api._
import {{ package.name }}.model.{{ product.name }}
import javax.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}


trait {{ product.name }}Component{% if product.type == "inherited" %} extends {{ base_product.name }}Component {% endif %}
{ self: HasDatabaseConfigProvider[JdbcProfile] =>
  class {{ product.name }}Table(tag: Tag)
    extends Table[{{ product.name }}](tag, "{{ product.name|upper()}}S") {

    {% if product.type == "inherited" %}
    val {{ base_product.name }}s = TableQuery[{{ base_product.name }}Table]

    def {{ base_product.name|lower() }}Id = column[{{base_product.properties[0].type}}]("{{ base_product.name|lower() }}_id")
    {% endif %}
    {% for property in product.properties %}
    def {{ property.name}} = column[{{property.type}}]("{{ property.name }}"{% if property.name == "id" %}, O.PrimaryKey, O.AutoInc{% endif %})
    {% endfor %}

    {% if product.type == "inherited" %}
    def {{ base_product.name|lower() }} = foreignKey("{{ base_product.name|lower() }}_FK", {{ base_product.name|lower() }}Id, {{ base_product.name }}s)(_.id)
    {% endif %}

    def * = (
    {%- if product.type == "inherited" %}
        {{- base_product.name|lower() }}Id{%- if True -%}, {% endif %}
    {%- endif %}
    {%- for property in product.properties %}
    {{- property.name}}
    {%- if not loop.last -%}, {% endif %}
    {% endfor %}) <> ({{ product.name }}.tupled, {{ product.name }}.unapply)
  }
}

@Singleton()
class {{ product.name }}Repository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                                  (implicit executionContext: ExecutionContext) extends {{ product.name }}Component
    with HasDatabaseConfigProvider[JdbcProfile] {

  val {{ product.name }}s = TableQuery[{{ product.name }}Table]

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
