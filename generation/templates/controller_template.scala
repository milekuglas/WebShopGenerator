package {{ package.name }}.controller

import javax.inject.{Inject, Singleton}
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}
import {{ package.name }}.service.{{ product.name }}Service

import scala.concurrent.ExecutionContext

@Singleton()
class {{ product.name }}Controller @Inject()(cc: ControllerComponents,
{{- product.name|lower() }}Service: {{ product.name }}Service)
                                   (implicit executionContext: ExecutionContext)
  extends AbstractController(cc) {


  def getAll = Action.async {
    {{ product.name|lower() }}Service.getAll map(result => Ok(Json.toJson(result)))
  }

  def get(id: Long) = Action.async {
    {{ product.name|lower() }}Service.get(id) map {
      case Some(result) =>
        Ok(Json.toJson(result))
      case None => NotFound

    }
  }
}

