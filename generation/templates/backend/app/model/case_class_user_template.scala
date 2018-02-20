package {{ package.name }}.model

case class User(id: Long,
                username: String,
                password: String,
                firstName: String,
                lastName: String,
                address: String,
                phone: String,
                email: String,
                money: Double,
                refreshToken: String)
