package {{ package.name }}.repository.table

import {{ package.name }}.model.User
import slick.jdbc.PostgresProfile.api._

class UserTable(tag: Tag) extends Table[User](tag, "USERS") {

  def id        = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def username  = column[String]("username", O.Unique)
  def password  = column[String]("password")
  def firstName = column[String]("first_name")
  def lastName  = column[String]("last_name")
  def address   = column[String]("address")
  def phone     = column[String]("phone")
  def email     = column[String]("email")
  def money     = column[Double]("money")
  def refreshToken = column[String]("refresh_token")

  def * = (id, username, password, firstName, lastName, address, phone, email, money, refreshToken) <> (User.tupled, User.unapply)
}

object UserTable {

  lazy val Users = TableQuery[UserTable]

}
