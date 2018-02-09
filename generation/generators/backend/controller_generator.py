'''
Created on Jan 9, 2018

@author: Milorad Vojnovic
'''


import os


from generation.generators.generator import Generator


class ControllerGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_base_controller_model(model)
        self.generate_controller_full_model(model)

    def generate_base_controller_model(self, model):
        self.main_generator.generate("backend/app/controller/controller_template.scala",
                 os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "controller"),
                 model.base_product.name + "Controller.scala",
                 {"package": model.project.package, "product": model.base_product})

    def generate_controller_full_model(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate("backend/app/controller/controller_full_template.scala",
                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "controller"),
                     model.products[i].name + "FullController.scala",
                     {"package": model.project.package, "product": model.products[i],
                      "base_product": model.base_product})

