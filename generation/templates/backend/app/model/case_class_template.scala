package {{ model.project.package.name }}.model
{% for property in product.properties %}
{%- if not property.primitive -%}
import {{ model.project.package.name }}.model.{{ property.type.name }}._
{% endif %}
{% endfor %}

case class {{ product.name }} (
  {% if product.type == "base" %}
  id: Long,
  {% endif %}
  {% if product.type == "inherited" %}
  {{ model.base_product.name|lower() }}Id: Long,
  {% endif %}
{% for property in product.properties %}
  {{ property.name }}: {{property.type.name}}
  {%- if not loop.last or product.type == "base" -%}
      ,
  {% endif %}
{% endfor %}
  {% if product.type == "base" %}
  categoryId: Long
  {% endif %}
)
