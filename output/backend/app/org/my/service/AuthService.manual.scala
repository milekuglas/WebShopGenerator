package org.my.service

import javax.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import com.github.t3hnar.bcrypt.Password
import org.my.exception.IncorrectPasswordException
import org.my.exception.InvalidRefreshTokenException
import org.my.exception.UserNotFoundException
import org.my.model.User
import org.my.repository.UserRepository
import org.my.util.JwtUtil
import org.my.service.AuthService

import play.api.Logger

class AuthServiceOtherImpl @Inject() (
  val userRepository: UserRepository,
  val jwtUtil: JwtUtil
)(implicit ec: ExecutionContext) extends AuthService {

  private val logger = Logger(this.getClass())

  def login(username: String, password: String): Future[(String, String)] = {
    userRepository.findByUsername(username).map { result =>
      result map { user =>
        if (password.isBcrypted(user.password)) {
          println("Added print to other implementation")
          logger.info("user successfully logged in using other service")
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

// object AuthService {

//   def apply(
//     userRepository: UserRepository,
//     jwtUtil: JwtUtil
//   )(implicit ec: ExecutionContext): AuthService =
//     new AuthServiceImpl(userRepository, jwtUtil)

// }