package {{ package.name }}.dto

import play.api.libs.json._
import {{ package.name }}.model.Category

case class PostCategory(
    name: String,
    superCategoryId: Option[Long]
)

object PostCategory {

  implicit val categoryReads = Json.reads[PostCategory]

  implicit def postCategoryToCategory(postCategory: PostCategory): Category =
    new Category(
      -1,
      postCategory.name,
      postCategory.superCategoryId
    )

}
