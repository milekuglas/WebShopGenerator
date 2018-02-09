package {{ package.name }}.service

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import {{ package.name }}.dto.{Get{{ product.name }}Full, Post{{ product.name }}Full}
import {{ package.name }}.repository.{{ product.name }}FullRepository


@Singleton()
class {{ product.name }}FullService @Inject()({{ product.name|lower() }}FullRepository:
{{- product.name }}FullRepository)(implicit executionContext: ExecutionContext) {

    def getAll: Future[Seq[Get{{ product.name }}Full]] = {
      {{ product.name|lower() }}FullRepository.all().map(_.map(Get
      {{- product.name }}Full.{{ product.name|lower() }}FullToGet{{ product.name }}Full))
    }

  def get(id: Long): Future[Option[Get{{ product.name }}Full]] = {
    {{ product.name|lower() }}FullRepository.get(id).map(_.map(Get
    {{- product.name }}Full.{{ product.name|lower() }}FullToGet{{ product.name }}Full))
  }

  def save({{ product.name|lower() }}Full: Post{{ product.name }}Full): Future[Get{{ product.name }}Full] = {
    {{ product.name|lower() }}FullRepository.insert({{ product.name|lower() }}Full).map(Get
    {{- product.name }}Full.{{ product.name|lower() }}FullToGet{{ product.name }}Full)
  }

  def delete(id: Long): Future[Int] = {
    {{ product.name|lower() }}FullRepository.delete(id)
  }

  def update(id: Long, {{ product.name|lower() }}Full: Post{{ product.name }}Full): Future[Int] = {
    {{ product.name|lower() }}FullRepository.update(id, {{ product.name|lower }}Full)
  }

  def search(
    page: Int,
    size: Int,
     {% for property in base_product.properties %}
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
            categoryId: Option[Long]): Future[Seq[Get{{ product.name }}Full]] = {

    {{ product.name|lower() }}FullRepository.search(
    page,
    size,
              {% for property in base_product.properties %}
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
                  categoryId).map(_.map(Get{{- product.name }}Full.{{- product.name | lower() }}FullToGet{{- product.name }}Full))

  }

}