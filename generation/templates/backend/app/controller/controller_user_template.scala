package {{ package.name }}.controller

import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import {{ package.name }}.dto.RegisterUser
import {{ package.name }}.dto.RegisteredUser
import {{ package.name }}.dto.JwtUser
import {{ package.name }}.service.UserService

import play.api.Logger
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.mvc.AbstractController
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.ControllerComponents

@Singleton()
class UserController @Inject() (
  val cc: ControllerComponents,
  val userService: UserService
)(implicit val ec: ExecutionContext)
  extends AbstractController(cc) {

  val logger: Logger = Logger(this.getClass())

  def register: Action[JsValue] = Action.async(parse.json) { request =>
    val optionalUser = request.body.validate[RegisterUser]
    optionalUser map { registerUser =>
      userService.register(registerUser) map { result =>
        logger.info("user registered successfully")
        val user: RegisteredUser = result
        Created(Json.toJson(user))
      } recover {
        case err =>
          logger.error(err.getMessage, err)
          BadRequest("Something went wrong.")
      }
    } getOrElse {
      logger.error("Invalid registration data.")
      Future { BadRequest("Invalid registration data.") }
    }
  }

  def me: Action[AnyContent] = Action.async { request =>

    val currentUser = request.attrs.get(JwtUser.Key).getOrElse {
        NotFound
    }.asInstanceOf[JwtUser]

    userService.get(currentUser.id).map { user =>
      Ok(Json.toJson(RegisteredUser.userToRegisteredUser(user)))
    }
  }
}
