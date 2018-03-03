import subprocess
import os
from sys import platform

from generation.generators.generator import Generator


class BaseAppGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_base_app(model)
        self.generate_environment(model)
        self.generate_app_component(model)

    def generate_base_app(self, model):
        shell = False
        if platform == 'win32':
            shell = True
        subprocess.check_call(["ng", "new", model.project.name, "--directory", "output/frontend"], shell=shell)
        self.main_generator.generate(
            "frontend/package.json",
            os.path.join("frontend"),
            "package.json",
            {}
        )
        self.main_generator.generate(
            "frontend/src/index.html",
            os.path.join("frontend", "src"),
            "index.html",
            {}
        )
        self.main_generator.generate(
            "frontend/src/styles.css",
            os.path.join("frontend", "src"),
            "styles.css",
            {}
        )

    def generate_environment(self, model):
        self.main_generator.generate(
            "frontend/src/environments/environment.ts",
            os.path.join("frontend", "src", "environments"),
            "environment.ts",
            {}
        )
        self.main_generator.generate(
            "frontend/src/environments/environment.prod.ts",
            os.path.join("frontend", "src", "environments"),
            "environment.prod.ts",
            {}
        )

    def generate_app_component(self, model):
        self.main_generator.generate(
            "frontend/src/app/app.component.html",
            os.path.join("frontend", "src", "app"),
            "app.component.html",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/app.component.css",
            os.path.join("frontend", "src", "app"),
            "app.component.css",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/app.component.ts",
            os.path.join("frontend", "src", "app"),
            "app.component.ts",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/app.module.ts",
            os.path.join("frontend", "src", "app"),
            "app.module.ts",
            {}
        )
        self.main_generator.generate(
            "frontend/src/app/app.routes.ts",
            os.path.join("frontend", "src", "app"),
            "app.routes.ts",
            {}
        )
