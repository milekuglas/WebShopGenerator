package {{ package.name }}.util

import scala.util.Failure
import scala.util.Success

import javax.inject.Inject

import {{ package.name }}.dto.JwtUser
import {{ package.name }}.dto.JwtUser.jwtUserFormat

import pdi.jwt.JwtAlgorithm
import pdi.jwt.JwtJson
import pdi.jwt.JwtOptions
import play.api.Configuration
import play.api.libs.json.JsError
import play.api.libs.json.JsObject
import play.api.libs.json.JsSuccess
import play.api.libs.json.Json
import play.api.mvc.RequestHeader
import play.api.Configuration

class JwtUtil @Inject() (config: Configuration) {

  private val header = "Authorization"
  private val algo   = JwtAlgorithm.HS256
  private val secret = config.get[String]("jwt.secret")

  def generate(jwtUser: JwtUser): String = {
    val claim: JsObject = Json.toJsObject(jwtUser)
    JwtJson.encode(claim, secret, algo)
  }

  def decode(request: RequestHeader): Option[JwtUser] = {
    val optionAuth = request.headers.get(header)
    val auth       = optionAuth getOrElse ""
    val jwt        = auth.stripPrefix("Bearer").trim
    val tryClaim   = JwtJson.decodeJson(jwt, secret, Seq(algo), JwtOptions(expiration = false))
    tryClaim match {
      case Success(claim) =>
        val result = Json.fromJson(claim)
        result match {
          case JsSuccess(user: JwtUser, _) =>
            Option(user)
          case _: JsError =>
            None
        }
      case Failure(_) =>
        None
    }
  }

  def isValid(token: String): Boolean = {
    val jwt = token.stripPrefix("Bearer").trim
    JwtJson.isValid(jwt, secret, Seq(algo))
  }

}
