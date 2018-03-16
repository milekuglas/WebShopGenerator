import os

from generation.generators.generator import Generator


class UserGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_registration_component(model)
        self.generate_shared(model)
        self.generate_module(model)

    def generate_registration_component(self, model):

        self.main_generator.generate(
            "frontend/src/app/user/registration/registration.component.html",
            os.path.join("frontend", "src", "app", "user", "registration"),
            "registration.component.html",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/user/registration/registration.component.css",
            os.path.join("frontend", "src", "app", "user", "registration"),
            "registration.component.css",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/user/registration/registration.component.ts",
            os.path.join("frontend", "src", "app", "user", "registration"),
            "registration.component.ts",
            {}
        )

    def generate_shared(self, model):
        self.main_generator.generate(
            "frontend/src/app/user/shared/registration-user.model.ts",
            os.path.join("frontend", "src", "app", "user", "shared"),
            "registration-user.model.ts",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/user/shared/user.model.ts",
            os.path.join("frontend", "src", "app", "user", "shared"),
            "user.model.ts",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/user/shared/user.service.ts",
            os.path.join("frontend", "src", "app", "user", "shared"),
            "user.service.ts",
            {}
        )

    def generate_module(self, model):
        self.main_generator.generate(
            "frontend/src/app/user/user.module.ts",
            os.path.join("frontend", "src", "app", "user"),
            "user.module.ts",
            {}
        )
