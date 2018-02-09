package {{ package.name }}.dto

import play.api.libs.json._
import {{ package.name }}.model.{{ product.name }}Full


case class Post{{ product.name }}Full(
                          {{ product.name|lower() }}: Post{{ product.name }},
                          {{ base_product.name|lower() }}: Post{{ base_product.name }}
                        )

object Post{{ product.name }}Full {

  implicit val {{ product.name|lower() }}FullReads = Json.reads[Post{{ product.name }}Full]

  implicit def post{{ product.name }}FullTo{{ product.name }}(post
  {{- product.name }}Full: Post{{ product.name }}Full): {{ product.name }}Full =
    new {{ product.name }}Full(
      post{{ product.name }}Full.{{ product.name|lower() }},
      post{{ product.name }}Full.{{ base_product.name|lower() }},
      null
    )

}
