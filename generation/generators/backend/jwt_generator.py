import os

from generation.generators.generator import Generator


class JWTGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_jwt_util(model)
        self.generate_jwt_filter(model)

    def generate_jwt_util(self, model):
        self.main_generator.generate(
            "backend/app/util/jwt_util_template.scala",
            os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "util"),
            "JwtUtil.scala",
            {"package": model.project.package}
        )

    def generate_jwt_filter(self, model):
        self.main_generator.generate(
            "backend/app/filter/jwt_filter_template.scala",
            os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "filter"),
            "JwtFilter.scala",
            {"package": model.project.package}
        )
