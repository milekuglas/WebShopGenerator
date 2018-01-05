package {{ model.project.package.name }}.model

case class {{ product.name }} (
{% for property in product.properties %}
  {{ property.name }}: {{property.type}}
  {%- if not loop.last -%}
      ,
  {% endif %}
{% endfor %}

)
