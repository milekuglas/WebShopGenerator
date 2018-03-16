package {{ package.name }}.dto

import play.api.libs.json._
import {{ package.name }}.model.{{ product.name }}


case class Get{{ product.name }}(
  {% if product.type == "base" %}
  id: Long,
  {% endif %}
{% if product.type == "inherited" %}
  {{ base_product.name|lower() }}Id: Long,
{% endif %}
{% for property in product.properties %}
  {{ property.name }}: {{property.type}}
  {%- if not loop.last or product.type == "base" -%}
      ,
  {% endif %}
{% endfor %}
  {% if product.type == "base" %}
  categoryId: Long
  {% endif %}
)

object Get{{ product.name }} {

  implicit val {{ product.name|lower() }}Writes = Json.writes[Get{{ product.name }}]

  implicit def {{ product.name|lower() }}ToGet
  {{- product.name }}({{ product.name|lower() }}: {{ product.name }}): Get{{ product.name }} =
    new Get{{ product.name }}(
    {% if product.type == "base" %}
     {{ product.name|lower() }}.id: Long,
    {% endif %}
    {% if product.type == "inherited" %}
      {{ product.name|lower() }}.{{ base_product.name|lower() }}Id,
    {% endif %}
     {% for property in product.properties %}
      {{ product.name|lower() }}.{{ property.name }}
      {%- if not loop.last or product.type == "base" -%}
      ,
      {% endif %}
    {% endfor %}
      {% if product.type == "base" %}
      {{ product.name|lower() }}.categoryId
      {% endif %}
    )
}
