export class Product {
    {% for property in product.properties %}
    {{ property.name }}: {% if property.type|string == 'String' %}string;
                         {% else %}number;
                         {% endif %}
    {% endfor %}
}