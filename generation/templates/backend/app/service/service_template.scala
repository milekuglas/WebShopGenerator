package {{ package.name }}.service

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import {{ package.name }}.dto.Get{{ product.name }}
import {{ package.name }}.repository.{{ product.name }}Repository


@Singleton()
class {{ product.name }}Service @Inject()({{ product.name|lower() }}Repository:
{{- product.name }}Repository)(implicit executionContext: ExecutionContext) {

  def get(id: Long): Future[Option[Get{{ product.name }}]] = {
    {{ product.name|lower() }}Repository.get(id).map(_.map(Get{{ product.name }}.{{ product.name|lower() }}ToGet{{ product.name }}))
  }

  def search(
  page: Int,
  size: Int,
  {% for property in product.properties %}
    {% if property.type.name != "Long" and property.type.name != "Int" 
  and property.type.name != "Double" and property.type.name != "Float" %}
            {{ property.name }}: Option[{{property.type}}],
    {% endif %}
  {% if (property.type.name == "Long" or property.type.name == "Int" 
  or property.type.name == "Double" or property.type.name == "Float") and property.name != "id" %}
            {{ property.name }}From: Option[{{property.type}}],
            {{ property.name }}To: Option[{{property.type}}],
    {% endif %}
{% endfor %}
            categoryId: Option[Long]): Future[Seq[Get{{ product.name }}]] = {

    {{ product.name|lower() }}Repository.search(
    page,
    size,
          {% for property in product.properties %}
        {% if property.type.name != "Long" and property.type.name != "Int" 
  and property.type.name != "Double" and property.type.name != "Float" %}
                  {{ property.name }},
      {% endif %}
        {% if (property.type.name == "Long" or property.type.name == "Int" 
  or property.type.name == "Double" or property.type.name == "Float") and property.name != "id" %}
                  {{ property.name }}From, 
                  {{ property.name }}To,
      {% endif %}
      {% endfor %}
                  categoryId).map(_.map(GetProduct.productToGetProduct))
  }
}
