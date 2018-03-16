package {{ package.name }}.exception

class IncorrectPasswordException(
    private val message: String = "Password didn't match with real password."
) extends Exception(message)
