package {{ package.name }}.dto

import {{ package.name }}.model.{{ product.name }}Full
import play.api.libs.json._


case class Get{{ product.name }}Full(
                         {{ product.name|lower() }}: Get{{ product.name }},
                         {{ base_product.name|lower() }}: Get{{ base_product.name }},
                         category: GetCategory
                       )

object Get{{ product.name }}Full {

  implicit val {{ product.name|lower() }}FullWrites = Json.writes[Get{{ product.name }}Full]

  implicit def {{ product.name|lower() }}FullToGet{{ product.name }}Full(
  {{- product.name|lower() }}Full: {{ product.name }}Full): Get{{ product.name }}Full =
    new Get{{ product.name }}Full(
      {{ product.name|lower() }}Full.{{ product.name|lower() }},
      {{ product.name|lower() }}Full.{{ base_product.name|lower() }},
      {{ product.name|lower() }}Full.category
    )
}
