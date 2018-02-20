package {{ package.name }}.service

import javax.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.util.Random

import com.github.t3hnar.bcrypt.Password
import {{ package.name }}.exception.UserNotFoundException
import {{ package.name }}.model.User
import {{ package.name }}.repository.UserRepository
import {{ package.name }}.dto.RegisteredUser

import play.api.Logger

class UserService @Inject() (val userRepository: UserRepository)(implicit val ec: ExecutionContext) {

  private val logger = Logger(this.getClass())

  def register(user: User): Future[User] = {
    val hashedUser =
      user.copy(password = user.password.bcrypt, refreshToken = generateRefreshToken)
    userRepository.insert(hashedUser) map { result =>
      logger.info("user created")
      result
    }
  }

  def get(id: Long): Future[User] = {
    userRepository.get(id) map { optionUser =>
      optionUser getOrElse { throw new UserNotFoundException("User doesn't exist.") }
    }
  }

  private def generateRefreshToken(): String = {
    Random.alphanumeric.take(50).mkString
  }

}

object UserService {

  def apply(userRepository: UserRepository)(implicit ec: ExecutionContext): UserService =
    new UserService(userRepository)

}
