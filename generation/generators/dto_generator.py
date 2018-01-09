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

    def generate_base_dto_get_model(self, model):
        self.main_generator.generate("dto_get_template.scala",
                 os.path.join("app", model.project.package.name.replace(".", os.sep), "dto"),
                 "Get" + model.base_product.name + ".scala",
                 {"package": model.project.package, "product": model.base_product})

    def generate_dto_get_model(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate("dto_get_template.scala",
                     os.path.join("app", model.project.package.name.replace(".", os.sep), "dto"),
                     "Get" + model.products[i].name + ".scala",
                     {"package": model.project.package, "product": model.products[i],
                      "base_product": model.base_product})

    def generate_dto_get_full_model(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate("dto_get_full_template.scala",
                     os.path.join("app", model.project.package.name.replace(".", os.sep), "dto"),
                     "Get" + model.products[i].name + "Full.scala",
                     {"package": model.project.package, "product": model.products[i],
                      "base_product": model.base_product})

    def generate_base_dto_post_model(self, model):
        self.main_generator.generate("dto_post_template.scala",
                         os.path.join("app", model.project.package.name.replace(".", os.sep), "dto"),
                         "Post" + model.base_product.name + ".scala",
                         {"package": model.project.package, "product": model.base_product})

    def generate_dto_post_model(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate("dto_post_template.scala",
                         os.path.join("app", model.project.package.name.replace(".", os.sep), "dto"),
                         "Post" + model.products[i].name + ".scala",
                         {"package": model.project.package, "product": model.products[i],
                          "base_product": model.base_product})

    def generate_dto_post_full_model(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate("dto_post_full_template.scala",
                         os.path.join("app", model.project.package.name.replace(".", os.sep), "dto"),
                         "Post" + model.products[i].name + "Full.scala",
                         {"package": model.project.package, "product": model.products[i],
                          "base_product": model.base_product})

