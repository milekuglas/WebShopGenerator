package {{ model.project.package.name }}.model

case class {{ product.name }} (
  {% if product.type == "inherited" %}
  {{ model.base_product.name|lower() }}Id: Long,
  {% endif %}
{% for property in product.properties %}
  {{ property.name }}: {{property.type}}
  {%- if not loop.last -%}
      ,
  {% endif %}
{% endfor %}

)
