package {{ model.project.package.name }}.model

import {{ model.project.package.name }}.model
import slick.jdbc.PostgresProfile.api._
import {{ model.project.package.name }}.util.EnumUtils
import play.api.libs.json.Format
import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import play.api.mvc.QueryStringBindable

case object {{ enum.name }} extends Enumeration {

  type {{ enum.name }} = Value

  {% for value in enum.values %}
  val {{ value }}: model.{{ enum.name }}.Value = Value("{{ value }}")
  {% endfor %}

  implicit val enumTypeFormat: Format[model.{{ enum.name }}.Value] = EnumUtils.enumFormat({{ enum.name }})

  implicit val {{ enum.name | lower() }}Mapper: JdbcType[{{ enum.name }}] with BaseTypedType[{{ enum.name }}] =
    MappedColumnType.base[{{ enum.name }}, String](
      e => e.toString,
      s => {{ enum.name }}.withName(s)
    )

  implicit object {{ enum.name | lower() }}SearchTypeQueryStringBinder
    extends QueryStringBindable.Parsing[{{ enum.name }}.Value](
      withName(_),
      _.toString,
      (k: String, e: Exception) => "Cannot parse %s as SearchTypes: %s".format(k, e.getMessage())
    )

}