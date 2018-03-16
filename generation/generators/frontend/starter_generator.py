import os

from generation.generators.generator import Generator


class StarterGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_starter_component(model)

    def generate_starter_component(self, model):

        self.main_generator.generate(
            "frontend/src/app/starter/starter.component.html",
            os.path.join("frontend", "src", "app", "starter"),
            "starter.component.html",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/starter/starter.component.css",
            os.path.join("frontend", "src", "app", "starter"),
            "starter.component.css",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/starter/starter.component.ts",
            os.path.join("frontend", "src", "app", "starter"),
            "starter.component.ts",
            {}
        )
