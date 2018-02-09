package {{ package.name }}.model

case class Category(
    id: Long,
    name: String,
    superCategoryId: Option[Long]
)
