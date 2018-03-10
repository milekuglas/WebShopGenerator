from generation.generators.generator import Generator
import os


class ShoppingCartGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_module(model)
        self.generate_service(model)

    def generate_module(self, model):
        self.main_generator.generate(
            "frontend/src/app/shopping-cart/shopping-cart.module.ts",
            os.path.join("frontend", "src", "app", "shopping-cart"),
            "shopping-cart.module.ts",
            {}
        )

    def generate_service(self, model):
        self.main_generator.generate(
            "frontend/src/app/shopping-cart/shopping-cart.service.ts",
            os.path.join("frontend", "src", "app", "shopping-cart"),
            "shopping-cart.service.ts",
            {}
        )
