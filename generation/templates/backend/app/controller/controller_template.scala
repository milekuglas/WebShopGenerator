package {{ package.name }}.controller

import javax.inject.{Inject, Singleton}
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}
import {{ package.name }}.service.{{ product.name }}Service

{% for property in product.properties %}
{%- if not property.primitive -%}
import {{ package.name }}.model.{{ property.type.name }}
{% endif %}
{% endfor %}

import scala.concurrent.ExecutionContext

@Singleton()
class {{ product.name }}Controller @Inject()(cc: ControllerComponents,
{{- product.name|lower() }}Service: {{ product.name }}Service)
                                   (implicit executionContext: ExecutionContext)
  extends AbstractController(cc) {

  def get(id: Long) = Action.async {
    {{ product.name|lower() }}Service.get(id) map {
      case Some(result) =>
        Ok(Json.toJson(result))
      case None => NotFound

    }
  }
    {% if product.type == "base" %}
  
  def getAll(
    page: Int,
    size: Int,
  {% for property in product.properties %}
    {% if property.type.name != "Long" and property.type.name != "Int" 
  and property.type.name != "Double" and property.type.name != "Float" %}
            {%- if property.primitive -%}
            {{ property.name }}: Option[{{property.type.name}}],
            {%- endif -%}
            {%- if not property.primitive -%}
            {{ property.name }}: Option[{{property.type.name}}.Value],
            {%- endif -%}
    {% endif %}
  {% if (property.type.name == "Long" or property.type.name == "Int" 
  or property.type.name == "Double" or property.type.name == "Float") and property.name != "id" %}
            {{ property.name }}From: Option[{{property.type.name}}],
            {{ property.name }}To: Option[{{property.type.name}}],
    {% endif %}
{% endfor %}
            categoryId: Option[Long]) = Action.async {

    {{ product.name|lower() }}Service.search(
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
                  categoryId) map (result => Ok(Json.toJson(result)))
  }
    {% endif %}
}

