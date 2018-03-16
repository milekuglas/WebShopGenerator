import os

from generation.generators.generator import Generator


class ExceptionGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_user_not_found_exception_model(model)
        self.generate_incorrect_password_exception_model(model)
        self.generate_invalid_refresh_token_exception_model(model)

    def generate_user_not_found_exception_model(self, model):
        self.main_generator.generate(
            "backend/app/exception/user_not_found_exception_template.scala",
            os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "exception"),
            "UserNotFoundException.scala",
            {"package": model.project.package}
        )

    def generate_incorrect_password_exception_model(self, model):
        self.main_generator.generate(
            "backend/app/exception/incorrect_password_exception_template.scala",
            os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "exception"),
            "IncorrectPasswordException.scala",
            {"package": model.project.package}
        )

    def generate_invalid_refresh_token_exception_model(self, model):
        self.main_generator.generate(
            "backend/app/exception/invalid_refresh_token_exception_template.scala",
            os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "exception"),
            "InvalidRefreshTokenException.scala",
            {"package": model.project.package}
        )
