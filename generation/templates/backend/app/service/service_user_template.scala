package {{ package.name }}.service

import javax.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.util.Random

import com.google.inject.ImplementedBy
import com.github.t3hnar.bcrypt.Password
import {{ package.name }}.exception.UserNotFoundException
import {{ package.name }}.model.{ShoppingCart, User}
import {{ package.name }}.repository.{ShoppingCartRepository, UserRepository}
import {{ package.name }}.dto.RegisteredUser

import play.api.Logger

@ImplementedBy(classOf[UserServiceImpl])
trait UserService {
  def register(user: User): Future[User]
  def get(id: Long): Future[User]
}

class UserServiceImpl @Inject() (val userRepository: UserRepository, val shoppingCartRepository: ShoppingCartRepository)(implicit val ec: ExecutionContext) extends UserService {

  private val logger = Logger(this.getClass())

  def register(user: User): Future[User] = {
    val hashedUser =
      user.copy(password = user.password.bcrypt, refreshToken = generateRefreshToken)
    userRepository.insert(hashedUser) map { result =>
      logger.info("user created")
      shoppingCartRepository.insert(ShoppingCart(-1, result.id))
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
