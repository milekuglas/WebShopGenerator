package {{ package.name }}.service

import javax.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import com.google.inject.ImplementedBy
import com.github.t3hnar.bcrypt.Password
import {{ package.name }}.exception.IncorrectPasswordException
import {{ package.name }}.exception.InvalidRefreshTokenException
import {{ package.name }}.exception.UserNotFoundException
import {{ package.name }}.model.User
import {{ package.name }}.repository.UserRepository
import {{ package.name }}.util.JwtUtil
import {{ package.name }}.service.AuthServiceOtherImpl

import play.api.Logger

@ImplementedBy(classOf[AuthServiceOtherImpl])
trait AuthService {
  def login(username: String, password: String): Future[(String, String)]
  def refresh(id: Long, refreshToken: String): Future[(String, String)]
}

class AuthServiceImpl @Inject() (
  val userRepository: UserRepository,
  val jwtUtil: JwtUtil
)(implicit ec: ExecutionContext) extends AuthService {

  private val logger = Logger(this.getClass())

  def login(username: String, password: String): Future[(String, String)] = {
    userRepository.findByUsername(username).map { result =>
      result map { user =>
        if (password.isBcrypted(user.password)) {
          logger.info("user successfully logged in")
          (jwtUtil.generate(user), user.refreshToken)
        } else {
          logger.error("user not logged in")
          throw new IncorrectPasswordException
        }
      } getOrElse {
        logger.error("user not found")
        throw new UserNotFoundException(s"User with username ${username} doesn't exist.")
      }
    }
  }

  def refresh(id: Long, refreshToken: String): Future[(String, String)] = {
    userRepository.get(id).map { userOption =>
      userOption map { user =>
        if (user.refreshToken == refreshToken) {
          logger.info("user successfully refreshed his token")
          (jwtUtil.generate(user), user.refreshToken)
        } else {
          logger.error("user sent invalid refresh token")
          throw new InvalidRefreshTokenException
        }
      } getOrElse {
        logger.error("user not found")
        throw new UserNotFoundException
      }
    }
  }

}
