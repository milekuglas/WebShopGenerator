import os

from generation.generators.generator import Generator


class CategoryGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_category_module(model)

    def generate_category_module(self, model):

        self.main_generator.generate(
            "frontend/src/app/category/shared/category.model.ts",
            os.path.join("frontend", "src", "app", "category", "shared"),
            "category.model.ts",
            {}
        )

        self.main_generator.generate(
            "frontend/src/app/category/shared/category.service.ts",
            os.path.join("frontend", "src", "app", "category", "shared"),
            "category.service.ts",
            {}
        )
