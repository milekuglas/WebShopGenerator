export class Product {
 id: number;
{% for property in product.properties %}
{% if property.type.name != "Long" and property.type.name != "Int"
and property.type.name != "Double" and property.type.name != "Float" %}

 {{ property.name }}: string;

{% else %}
 {{ property.name }}: number;
{% endif %}
{% endfor %}
categoryId: number;
}