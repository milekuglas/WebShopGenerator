package {{ package.name }}.repository.table

import {{ package.name }}.model.{{ product.name }}
import slick.jdbc.PostgresProfile.api._
{% for property in product.properties %}
{%- if not property.primitive -%}
import {{ package.name }}.model.{{ property.type.name }}._
{% endif %}
{% endfor %}

  class {{ product.name }}Table(tag: Tag) extends Table[{{ product.name }}](tag, "{{ product.name|upper()}}S") {

    val Categories = TableQuery[CategoryTable]

    {% if product.type == "base" %}
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    {% endif %}
    {% if product.type == "inherited" %}
    val {{ base_product.name }}s = TableQuery[{{ base_product.name }}Table]

    def {{ base_product.name|lower() }}Id = column[Long]("{{ base_product.name|lower() }}_id")
    {% endif %}
    {% for property in product.properties %}
    def {{ property.name}} = column[{{property.type.name}}]("{{ property.name }}"{% if property.name == "id" %}, O.PrimaryKey, O.AutoInc{% endif %})
    {% endfor %}

    {% if product.type == "inherited" %}
    def {{ base_product.name|lower() }} = foreignKey("{{ base_product.name|lower() }}_FK", {{ base_product.name|lower() }}Id, {{ base_product.name }}s)(_.id)
    {% endif %}
    {% if product.type == "base" %}
    def categoryId = column[Long]("category_id")

    def category = foreignKey("category_FK", categoryId, Categories)(_.id)
    {% endif %}

    def * = (
    {%- if product.type == "base" -%} id, {% endif %}
    {%- if product.type == "inherited" %}
        {{- base_product.name|lower() }}Id{%- if True -%}, {% endif %}
    {%- endif %}
    {%- for property in product.properties %}
    {{- property.name}}
    {%- if not loop.last or product.type == "base" -%}, {% endif %}
    {% endfor %}
    {%- if product.type == "base" -%} categoryId {% endif %}) <> ({{ product.name }}.tupled, {{ product.name }}.unapply)
}

object {{ product.name }}Table {

  lazy val {{ product.name }}s = TableQuery[{{ product.name }}Table]

}