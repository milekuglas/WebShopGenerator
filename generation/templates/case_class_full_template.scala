package {{ package.name }}.model

case class {{ product.name }}Full({{ product.name|lower() }}: {{ product.name }}, 
 {{- base_product.name|lower() }}: {{ base_product.name }}, category: Category)
