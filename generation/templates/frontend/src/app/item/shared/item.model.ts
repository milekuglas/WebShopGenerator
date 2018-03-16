export class {{product.name|capitalize}} {
    {{base_product.name|lower}}: {
        id: number;
        {% for property in base_product.properties %}
        {{ property.name }}: {% if property.type|string == 'String' %}string;
        {% else %}number;
        {% endif %}
        {% endfor %}
    };
    {{product.name|lower}}: {
        {% for property in product.properties %}
        {{ property.name }}: {% if property.type|string == 'String' %}string;
        {% else %}number;
        {% endif %}
        {% endfor %}
    };
}
