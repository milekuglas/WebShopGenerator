'''
Created on Jan 5, 2018

@author: Milorad Vojnovic
'''

import os

from generation.generators.generator import Generator


class ModelGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_base_product_model(model)
        self.generate_products_model(model)
        self.generate_products_full_model(model)

    def generate_base_product_model(self, model):
        self.main_generator.generate("backend/app/model/case_class_template.scala",
                 os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "model"),
                 model.base_product.name + ".scala", {"model": model, "product": model.base_product})

    def generate_products_model(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate("backend/app/model/case_class_template.scala",
                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "model"),
                     model.products[i].name + ".scala", {"model": model, "product": model.products[i]})

    def generate_products_full_model(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate("backend/app/model/case_class_full_template.scala",
                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "model"),
                     model.products[i].name + "Full.scala",
                     {"package": model.project.package, "product": model.products[i],
                      "base_product": model.base_product})
