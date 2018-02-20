package {{ package.name }}.controller

import javax.inject.{Inject, Singleton}
import {{ package.name }}.dto.Post{{ product.name }}Full
import {{ package.name }}.service.{{ product.name }}FullService
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import scala.concurrent.{ExecutionContext, Future}


@Singleton()
class {{ product.name }}FullController @Inject()(cc: ControllerComponents,
{{- product.name|lower() }}FullService: {{ product.name }}FullService)
                                       (implicit executionContext: ExecutionContext)
  extends AbstractController(cc) {

  def get(id: Long) = Action.async {
    {{ product.name|lower() }}FullService.get(id) map {
      case Some(result) => Ok(Json.toJson(result))
      case None => NotFound
    }
  }

  def add: Action[JsValue] = Action.async(parse.json) { request =>
    val optional{{ product.name }}Full = request.body.validate[Post{{ product.name }}Full]
    optional{{ product.name }}Full match {
      case JsSuccess(post{{ product.name }}Full: Post{{ product.name }}Full, _) =>
        {{ product.name|lower() }}FullService.save(post{{ product.name }}Full) map { result => Created(Json.toJson(result)) }
      case _: JsError => Future.successful(BadRequest)
    }
  }

  def delete(id: Long) = Action.async {
    {{ product.name|lower() }}FullService.delete(id) map {
      case x if x < 1 => NotFound
      case _ => Ok
    }
  }

  def update(id: Long): Action[JsValue] = Action.async(parse.json) { request =>
    val optional{{ product.name }}Full = request.body.validate[Post{{ product.name }}Full]
    optional{{ product.name }}Full match {
      case JsSuccess(post{{ product.name }}Full: Post{{ product.name }}Full, _) =>
        {{ product.name|lower() }}FullService.update(id, post{{ product.name }}Full) map { result => Ok(Json.toJson(result)) }
      case _:JsError => Future.successful(BadRequest)
    }
  }

  def getAll(
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
            categoryId: Option[Long]) = Action.async {

    {{ product.name|lower() }}FullService.search(
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
                  categoryId) map (result => Ok(Json.toJson(result)))
  }
}


