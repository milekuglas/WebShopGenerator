'''
Created on Jan 9, 2018

@author: Milorad Vojnovic
'''


import os


from generation.generators.generator import Generator


class ServiceGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_base_service_model(model)
        self.generate_service_full_model(model)

    def generate_base_service_model(self, model):
        self.main_generator.generate("backend/app/service/service_template.scala",
                 os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "service"),
                 model.base_product.name + "Service.scala",
                 {"package": model.project.package, "product": model.base_product})

    def generate_service_full_model(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate("backend/app/service/service_full_template.scala",
                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "service"),
                     model.products[i].name + "FullService.scala",
                     {"package": model.project.package, "product": model.products[i],
                      "base_product": model.base_product})

