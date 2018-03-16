package {{ package.name }}.dto

import play.api.libs.json._
import {{ package.name }}.model.{{ product.name }}
{% for property in product.properties %}
{%- if not property.primitive -%}
import {{ package.name }}.model.{{ property.type.name }}._
{% endif %}
{% endfor %}


case class Post{{ product.name }}(
{% for property in product.properties %}
{% if not property.name == "id" %}
    {{ property.name }}: {{property.type.name}}
  {%- if not loop.last  or product.type == "base" -%}
      ,
  {% endif %}
  {% endif %}
{% endfor %}
    {% if product.type == "base" %}
    categoryId: Long
    {% endif %}

)

object Post{{ product.name }} {

  implicit val {{ product.name|lower() }}Reads = Json.reads[Post{{ product.name }}]

  implicit def post{{ product.name }}To{{ product.name }}(post
  {{- product.name }}: Post{{ product.name }}): {{ product.name }} =
    new {{ product.name }}(
      -1,
{% for property in product.properties %}
{% if not property.name == "id" %}
      post{{ product.name }}.{{ property.name }}
  {%- if not loop.last  or product.type == "base" -%}
      ,
  {% endif %}
  {% endif %}
{% endfor %}
    {% if product.type == "base" %}
      post{{ product.name }}.categoryId
    {% endif %}
    )
}
