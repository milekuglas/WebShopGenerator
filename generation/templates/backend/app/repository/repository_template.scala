package {{ package.name }}.repository

import slick.jdbc.PostgresProfile.api._
import {{ package.name }}.model.{{ product.name }}
import {{ package.name }}.repository.table.{{ product.name }}Table
import {{ package.name }}.search.filter.MaybeFilter
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.{ExecutionContext, Future}
{% for property in product.properties %}
{%- if not property.primitive -%}
import {{ package.name }}.model.{{ property.type.name }}._
{% endif %}
{% endfor %}

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

{% if product.type == "base" %}
    def search(
        page: Int,
        size: Int,
      {% for property in product.properties %}
    {% if property.type.name != "Long" and property.type.name != "Int" 
  and property.type.name != "Double" and property.type.name != "Float" %}
            {{ property.name }}: Option[{{property.type.name}}],
    {% endif %}
  {% if (property.type.name == "Long" or property.type.name == "Int" 
  or property.type.name == "Double" or property.type.name == "Float") and property.name != "id" %}
            {{ property.name }}From: Option[{{property.type.name}}],
            {{ property.name }}To: Option[{{property.type.name}}],
    {% endif %}
{% endfor %}
            categoryId: Option[Long]): Future[Seq[{{ product.name }}]] =
    db.run(find(
    {% for property in product.properties %}
        {% if property.type.name != "Long" and property.type.name != "Int" 
  and property.type.name != "Double" and property.type.name != "Float" %}
                  {{ property.name }},
      {% endif %}
        {% if (property.type.name == "Long" or property.type.name == "Int" 
  or property.type.name == "Double" or property.type.name == "Float") and property.name != "id" %}
                  {{ property.name }}From, 
                  {{ property.name }}To,
      {% endif %}
      {% endfor %}
                  categoryId).drop((page - 1) * size).take(size).result)

  def find(
        {% for property in product.properties %}
    {% if property.type.name != "Long" and property.type.name != "Int" 
  and property.type.name != "Double" and property.type.name != "Float" %}
            {{ property.name }}: Option[{{property.type.name}}],
    {% endif %}
  {% if (property.type.name == "Long" or property.type.name == "Int" 
  or property.type.name == "Double" or property.type.name == "Float") and property.name != "id" %}
            {{ property.name }}From: Option[{{property.type.name}}],
            {{ property.name }}To: Option[{{property.type.name}}],
    {% endif %}
{% endfor %}
            categoryId: Option[Long]) = {

    MaybeFilter({{ product.name }}s)
    {% for property in product.properties %}
    {%- if property.primitive -%}
    {% if property.type.name != "Long" and property.type.name != "Int" 
  and property.type.name != "Double" and property.type.name != "Float" %}
      .filter({{ property.name }})(value => {{ product.name|lower() }} => {{ product.name|lower() }}.{{ property.name }}.toLowerCase like "%"+value.toLowerCase+"%")
      {% endif %}
    {% if (property.type.name == "Long" or property.type.name == "Int" 
  or property.type.name == "Double" or property.type.name == "Float") and property.name != "id" %}
      .filter({{ property.name }}From)(value => {{ product.name|lower() }} => {{ product.name|lower() }}.{{ property.name }} >= value)
      .filter({{ property.name }}To)(value => {{ product.name|lower() }} => {{ product.name|lower() }}.{{ property.name }} <= value)
      {% endif %}
      {% endif %}
        {%- if not property.primitive -%}
        .filter({{ property.name }})(value => {{ product.name|lower() }} => {{ product.name|lower() }}.{{ property.name }} === value)
        {% endif %}
    {% endfor %}
      .filter(categoryId)(value => product => product.categoryId === categoryId)
      .query
  }
  {% endif %}

  def create(): Future[Unit] = db.run({{ product.name }}s.schema.create)

  {% if product.type == "base" %}
  def count(): Future[Int] = db.run({{ product.name }}s.length.result)
  {% endif %}
}
