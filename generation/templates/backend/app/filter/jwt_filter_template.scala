package {{ package.name }}.filter

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.util.Try

import javax.inject.Inject

import {{ package.name }}.dto.JwtUser
import {{ package.name }}.util.JwtUtil

import akka.stream.Materializer
import play.api.mvc.Filter
import play.api.mvc.RequestHeader
import play.api.mvc.Result
import play.api.mvc.Results.Unauthorized
import play.api.routing.Router

class JwtFilter @Inject() (jwtUtil: JwtUtil)(
  implicit val mat: Materializer,
  implicit val ec: ExecutionContext
) extends Filter {

  private val header = "Authorization"

  def apply(nextFilter: RequestHeader => Future[Result])(
    requestHeader: RequestHeader): Future[Result] = {

    val handler   = Try(requestHeader.attrs(Router.Attrs.HandlerDef))
    val modifiers = handler.map(_.modifiers).getOrElse(Seq())

    if (modifiers.contains("noauth")) {
      nextFilter(requestHeader)
    } else if (requestHeader.headers.hasHeader(header)) {
      val authOption = requestHeader.headers.get(header)
      authOption map { auth =>
        if (jwtUtil.isValid(auth)) {
          jwtUtil.decode(requestHeader) map { jwtUser =>
            val req = requestHeader.addAttr(JwtUser.Key, jwtUser)
            nextFilter(req)
          } getOrElse {
            Future { Unauthorized("You are not logged in.") }
          }
        } else {
          Future { Unauthorized("You are not logged in.") }
        }
      } getOrElse {
        Future { Unauthorized("You are not logged in.") }
      }
    } else {
      Future { Unauthorized("You are not logged in.") }
    }
  }

}

object JwtFilter {

  def apply(jwtUtil: JwtUtil)(implicit mat: Materializer, ec: ExecutionContext): JwtFilter = {
    new JwtFilter(jwtUtil)
  }

}
