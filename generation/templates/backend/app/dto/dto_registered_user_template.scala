package {{ package.name }}.dto

import {{ package.name }}.model.User

import play.api.libs.json.Json

case class RegisteredUser(
  id: Long,
  username: String,
  firstName: String,
  lastName: String,
  address: String,
  phone: String,
  email: String
)

object RegisteredUser {

  implicit val registeredUserWrites = Json.writes[RegisteredUser]

  implicit def userToRegisteredUser(user: User): RegisteredUser =
    new RegisteredUser(
      user.id,
      user.username,
      user.firstName,
      user.lastName,
      user.address,
      user.phone,
      user.email
    )

}
