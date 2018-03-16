package {{ package.name }}.controller

import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import {{ package.name }}.dto.LoginUser
import {{ package.name }}.dto.LoggedInUser
import {{ package.name }}.dto.RefreshToken
import {{ package.name }}.service.AuthService
import {{ package.name }}.util.JwtUtil

import play.api.Logger
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.mvc.AbstractController
import play.api.mvc.Action
import play.api.mvc.ControllerComponents

@Singleton()
class AuthController @Inject() (
  val cc: ControllerComponents,
  val jwtUtil: JwtUtil,
  val authService: AuthService
)(implicit val ec: ExecutionContext)
    extends AbstractController(cc) {

  val logger: Logger = Logger(this.getClass())

  private val header = "Authorization"

  def login: Action[JsValue] = Action.async(parse.json) { request =>
    val optionalUser = request.body.validate[LoginUser]
    optionalUser map { loginUser =>
      authService.login(loginUser.username, loginUser.password) map {
        case (accessToken, refreshToken) =>
          val loggedInUser = LoggedInUser(accessToken, refreshToken)
          logger.info("user successfully logged in")
          Ok(Json.toJson(loggedInUser))
      } recover {
        case err =>
          logger.error(err.getMessage, err)
          BadRequest("Something went wrong.")
      }
    } getOrElse {
      logger.error("user sent invalid login data")
      Future {
        BadRequest("Invalid log in data.")
      }
    }
  }

  def refresh: Action[JsValue] = Action.async(parse.json) { request =>
    val optionToken = request.body.validate[RefreshToken]
    optionToken map { refreshToken =>
      val id: Long = jwtUtil.decode(request) map { _.id } getOrElse { -1 }
      authService.refresh(id, refreshToken.token) map {
        case (accessToken, refreshToken) =>
          val loggedInUser = LoggedInUser(accessToken, refreshToken)
          logger.info("refreshed jwt successfully")
          Ok(Json.toJson(loggedInUser))
      } recover {
        case err =>
          logger.error(err.getMessage, err)
          BadRequest("Invalid refresh token.")
      }
    } getOrElse {
      logger.error("user sent invalid refresh token data")
      Future {
        BadRequest("Invalid data format.")
      }
    }
  }

}
