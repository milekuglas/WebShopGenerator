import os

from generation.generators.generator import Generator


class AuthGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_login_component(model)
        self.generate_shared(model)
        self.generate_module(model)

    def generate_login_component(self, model):

        # Login
        self.main_generator.generate(
            "frontend/src/app/auth/login/login.component.html",
            os.path.join("frontend", "src", "app", "auth", "login"),
            "login.component.html",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/auth/login/login.component.css",
            os.path.join("frontend", "src", "app", "auth", "login"),
            "login.component.css",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/auth/login/login.component.ts",
            os.path.join("frontend", "src", "app", "auth", "login"),
            "login.component.ts",
            {}
        )

    def generate_shared(self, model):
        self.main_generator.generate(
            "frontend/src/app/auth/shared/auth.interceptor.ts",
            os.path.join("frontend", "src", "app", "auth", "shared"),
            "auth.interceptor.ts",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/auth/shared/auth.service.ts",
            os.path.join("frontend", "src", "app", "auth", "shared"),
            "auth.service.ts",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/auth/shared/auth-guard.service.ts",
            os.path.join("frontend", "src", "app", "auth", "shared"),
            "auth-guard.service.ts",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/auth/shared/logged-in-user.model.ts",
            os.path.join("frontend", "src", "app", "auth", "shared"),
            "logged-in-user.model.ts",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/auth/shared/login-user.model.ts",
            os.path.join("frontend", "src", "app", "auth", "shared"),
            "login-user.model.ts",
            {}
        )

    def generate_module(self, model):
        self.main_generator.generate(
            "frontend/src/app/auth/auth.module.ts",
            os.path.join("frontend", "src", "app", "auth"),
            "auth.module.ts",
            {}
        )
