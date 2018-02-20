'''
Created on Jan 9, 2018

@author: Milorad Vojnovic
'''


import os


from generation.generators.generator import Generator


class DTOGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_base_dto_get_model(model)
        self.generate_dto_get_model(model)
        self.generate_dto_get_full_model(model)
        self.generate_base_dto_post_model(model)
        self.generate_dto_post_model(model)
        self.generate_dto_post_full_model(model)
        self.generate_dto_jwt_user_model(model)
        self.generate_dto_login_user_model(model)
        self.generate_dto_logged_in_user_model(model)
        self.generate_dto_register_user_model(model)
        self.generate_dto_registered_user_model(model)
        self.generate_dto_refresh_token_model(model)

    def generate_base_dto_get_model(self, model):
        self.main_generator.generate("backend/app/dto/dto_get_template.scala",
                 os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "dto"),
                 "Get" + model.base_product.name + ".scala",
                 {"package": model.project.package, "product": model.base_product})

    def generate_dto_get_model(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate("backend/app/dto/dto_get_template.scala",
                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "dto"),
                     "Get" + model.products[i].name + ".scala",
                     {"package": model.project.package, "product": model.products[i],
                      "base_product": model.base_product})

    def generate_dto_get_full_model(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate("backend/app/dto/dto_get_full_template.scala",
                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "dto"),
                     "Get" + model.products[i].name + "Full.scala",
                     {"package": model.project.package, "product": model.products[i],
                      "base_product": model.base_product})

    def generate_base_dto_post_model(self, model):
        self.main_generator.generate("backend/app/dto/dto_post_template.scala",
                         os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "dto"),
                         "Post" + model.base_product.name + ".scala",
                         {"package": model.project.package, "product": model.base_product})

    def generate_dto_post_model(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate("backend/app/dto/dto_post_template.scala",
                         os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "dto"),
                         "Post" + model.products[i].name + ".scala",
                         {"package": model.project.package, "product": model.products[i],
                          "base_product": model.base_product})

    def generate_dto_post_full_model(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate("backend/app/dto/dto_post_full_template.scala",
                         os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "dto"),
                         "Post" + model.products[i].name + "Full.scala",
                         {"package": model.project.package, "product": model.products[i],
                          "base_product": model.base_product})

    def generate_dto_jwt_user_model(self, model):
        self.main_generator.generate(
            "backend/app/dto/dto_jwt_user_template.scala",
            os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "dto"),
            "JwtUser.scala",
            {"package": model.project.package}
        )

    def generate_dto_login_user_model(self, model):
        self.main_generator.generate(
            "backend/app/dto/dto_login_user_template.scala",
            os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "dto"),
            "LoginUser.scala",
            {"package": model.project.package}
        )

    def generate_dto_logged_in_user_model(self, model):
        self.main_generator.generate(
            "backend/app/dto/dto_logged_in_user_template.scala",
            os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "dto"),
            "LoggedInUser.scala",
            {"package": model.project.package}
        )

    def generate_dto_register_user_model(self, model):
        self.main_generator.generate(
            "backend/app/dto/dto_register_user_template.scala",
            os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "dto"),
            "RegisterUser.scala",
            {"package": model.project.package}
        )

    def generate_dto_registered_user_model(self, model):
        self.main_generator.generate(
            "backend/app/dto/dto_registered_user_template.scala",
            os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "dto"),
            "RegisteredUser.scala",
            {"package": model.project.package}
        )

    def generate_dto_refresh_token_model(self, model):
        self.main_generator.generate(
            "backend/app/dto/dto_refresh_token_template.scala",
            os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "dto"),
            "RefreshToken.scala",
            {"package": model.project.package}
        )