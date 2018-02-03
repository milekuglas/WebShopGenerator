package {{ package.name }}.model

case class Category(
    id: Long,
    name: String,
    subcategoryId: Option[Long]
)
