import os

from generation.generators.generator import Generator


class HomeGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_home_component(model)

    def generate_home_component(self, model):

        self.main_generator.generate(
            "frontend/src/app/home/home.component.html",
            os.path.join("frontend", "src", "app", "home"),
            "home.component.html",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/home/home.component.css",
            os.path.join("frontend", "src", "app", "home"),
            "home.component.css",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/home/home.component.ts",
            os.path.join("frontend", "src", "app", "home"),
            "home.component.ts",
            {}
        )
