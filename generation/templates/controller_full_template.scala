package {{ package.name }}.controller

import javax.inject.{Inject, Singleton}

import dto.Post{{ product.name }}Full
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import service.{{ product.name }}FullService

import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class {{ product.name }}FullController @Inject()(cc: ControllerComponents,
{{- product.name|lower() }}FullService: {{ product.name }}FullService)
                                       (implicit executionContext: ExecutionContext)
  extends AbstractController(cc) {


  def getAll = Action.async {
    {{ product.name|lower() }}FullService.getAll map({{ product.name|lower() }}s => Ok(Json.toJson({{ product.name|lower() }}s)))
  }

  def get(id: Long) = Action.async {
    {{ product.name|lower() }}FullService.get(id) map {
      case Some({{ product.name|lower() }}) => Ok(Json.toJson({{ product.name|lower() }}))
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
}


