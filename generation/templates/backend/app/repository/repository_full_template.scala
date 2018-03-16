package {{ package.name }}.repository

import slick.jdbc.PostgresProfile.api._
import {{ package.name }}.model.{ Category,  {{ product.name }}, {{product.name}}Full }
import javax.inject.{Inject, Singleton}
import {{ package.name }}.search.filter.MaybeFilter
import play.api.db.slick.DatabaseConfigProvider
import {{ package.name }}.repository.table.{CategoryTable, {{ product.name }}Table, {{base_product.name}}Table}
import scala.concurrent.{ExecutionContext, Future}
{% for property in product.properties %}
{%- if not property.primitive -%}
import {{ package.name }}.model.{{ property.type.name }}._
{% endif %}
{% endfor %}
{% for property in base_product.properties %}
{%- if not property.primitive -%}
import {{ package.name }}.model.{{ property.type.name }}._
{% endif %}
{% endfor %}


@Singleton()
class {{ product.name }}FullRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider,
                                                  {{ base_product.name | lower() }}Repository: {{ base_product.name }}Repository)
                                    (implicit executionContext: ExecutionContext) {

  private val {{ product.name }}s = TableQuery[{{ product.name }}Table]
  private val {{ base_product.name }}s = TableQuery[{{ base_product.name }}Table]
  private val Categories = TableQuery[CategoryTable]

  val db = dbConfigProvider.get.db

  def all(): Future[Seq[{{ product.name }}Full]] = db.run({
    for {
      (({{ product.name|lower() }}, {{ base_product.name|lower() }}), category) <- {{ product.name }}s join {{ base_product.name }}s on (_.
      {{- base_product.name|lower() }}Id === _.id)  join Categories on (_._2.categoryId === _.id)
    } yield ({{ product.name|lower() }}, {{ base_product.name|lower() }}, category)
  }.result.map(_ map {
    case ({{ product.name|lower() }}: {{ product.name }}, {{ base_product.name|lower() }}:{{ base_product.name}}, category: Category) =>
    {{- product.name }}Full({{ product.name|lower() }}, {{ base_product.name|lower() }}, category)
  })
  )

  def get(id: Long):Future[Option[{{ product.name }}Full]] = db.run({
    for {
      (({{ product.name|lower() }}, {{ base_product.name|lower() }}), category) <-
      {{- product.name }}s.filter(_.{{ base_product.name|lower()}}Id === id) join {{ base_product.name}}s on (_.
      {{- base_product.name|lower()}}Id === _.id)  join Categories on (_._2.categoryId === _.id)
    } yield ({{ product.name|lower() }}, {{ base_product.name|lower() }}, category)
  }.result.head map {
    case ({{ product.name|lower() }}: {{ product.name }},
    {{- base_product.name|lower() }}:{{ base_product.name }}, category: Category) => Some(
    {{- product.name }}Full({{ product.name|lower() }}, {{ base_product.name|lower() }}, category))
    case _ => None
  })

  def insert({{ product.name|lower() }}Full: {{ product.name }}Full): Future[{{ product.name }}Full] = db.run((for {
    category <- Categories.filter(_.name === "{{ product.name }}").result.head
    {{ base_product.name|lower() }} <- ({{ base_product.name }}s returning {{ base_product.name }}s) += {{ product.name|lower() }}Full.
    {{- base_product.name|lower() }}.copy(categoryId = category.id)
    {{ product.name|lower() }} <- ({{ product.name }}s returning {{ product.name }}s) += {{ product.name|lower() }}Full.
    {{- product.name|lower() }}.copy({{ base_product.name|lower() }}Id = {{ base_product.name|lower() }}.id)
  } yield {{ product.name }}Full({{ product.name|lower() }}, {{ base_product.name|lower() }}, category)).transactionally)

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

    def search(
        page: Int,
        size: Int,
         {% for property in base_product.properties %}
    {% if property.type.name != "Long" and property.type.name != "Int" 
  and property.type.name != "Double" and property.type.name != "Float" %}
            {{ property.name }}: Option[{{property.type.name}}],
    {% endif %}
  {% if (property.type.name == "Long" or property.type.name == "Int" 
  or property.type.name == "Double" or property.type.name == "Float") and property.name != "id" %}
            {{ property.name }}From: Option[{{property.type}}],
            {{ property.name }}To: Option[{{property.type.name}}],
    {% endif %}
{% endfor %}
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
            categoryId: Option[Long]): Future[Seq[{{ product.name }}Full]] = {

    db.run((for {
      (({{ product.name|lower() }}, {{ base_product.name|lower() }}), category) <- find(
        {% for property in product.properties %}
        {% if property.type.name != "Long" and property.type.name != "Int" 
  and property.type.name != "Double" and property.type.name != "Float" %}
                  {{ property.name }}
                  {%- if not loop.last -%}
                  , 
                  {% endif %}
      {% endif %}
        {% if (property.type.name == "Long" or property.type.name == "Int" 
  or property.type.name == "Double" or property.type.name == "Float") and property.name != "id" %}
                  {{ property.name }}From, 
                  {{ property.name }}To
                  {%- if not loop.last -%}
                  , 
                  {% endif %}
      {% endif %}
      {% endfor %}) join {{ base_product.name|lower() }}Repository.find(
              {% for property in base_product.properties %}
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
        categoryId) on (_.{{ base_product.name|lower() }}Id === _.id) join Categories on (_._2.categoryId === _.id)
    } yield ({{ product.name|lower() }}, {{ base_product.name|lower() }}, category)).drop((page - 1) * size).take(size).result.map(_ map {
      case ({{ product.name|lower() }}: {{ product.name }}, 
      {{- base_product.name|lower() -}}: {{ base_product.name }}, category: Category) =>
        {{ product.name }}Full({{ product.name|lower() }}, {{ base_product.name|lower() }}, category)
    }))
  }

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
{% endfor %}) = {

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
      .query
  }
}