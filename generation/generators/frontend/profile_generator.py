import os

from generation.generators.generator import Generator


class ProfileGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_profile_component(model)
        self.generate_module(model)

    def generate_profile_component(self, model):
        self.main_generator.generate(
            "frontend/src/app/profile/profile.component.html",
            os.path.join("frontend", "src", "app", "profile"),
            "profile.component.html",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/profile/profile.component.css",
            os.path.join("frontend", "src", "app", "profile"),
            "profile.component.css",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/profile/profile.component.ts",
            os.path.join("frontend", "src", "app", "profile"),
            "profile.component.ts",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/profile/user-info/user-info.component.html",
            os.path.join("frontend", "src", "app", "profile/user-info"),
            "user-info.component.html",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/profile/user-info/user-info.component.css",
            os.path.join("frontend", "src", "app", "profile/user-info"),
            "user-info.component.css",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/profile/user-info/user-info.component.ts",
            os.path.join("frontend", "src", "app", "profile/user-info"),
            "user-info.component.ts",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/profile/shopping-cart/shopping-cart.component.html",
            os.path.join("frontend", "src", "app", "profile/shopping-cart"),
            "shopping-cart.component.html",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/profile/shopping-cart/shopping-cart.component.css",
            os.path.join("frontend", "src", "app", "profile/shopping-cart"),
            "shopping-cart.component.css",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/profile/shopping-cart/shopping-cart.component.ts",
            os.path.join("frontend", "src", "app", "profile/shopping-cart"),
            "shopping-cart.component.ts",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/profile/shopping-history/shopping-history.component.html",
            os.path.join("frontend", "src", "app", "profile/shopping-history"),
            "shopping-history.component.html",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/profile/shopping-history/shopping-history.component.css",
            os.path.join("frontend", "src", "app", "profile/shopping-history"),
            "shopping-history.component.css",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/profile/shopping-history/shopping-history.component.ts",
            os.path.join("frontend", "src", "app", "profile/shopping-history"),
            "shopping-history.component.ts",
            {}
        )

    def generate_module(self, model):
        self.main_generator.generate(
            "frontend/src/app/profile/profile.module.ts",
            os.path.join("frontend", "src", "app", "profile"),
            "profile.module.ts",
            {}
        )
