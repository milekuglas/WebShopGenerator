'''
Created on Feb 9, 2018

@author: Milorad Vojnovic
'''

import os

from generation.generators.generator import Generator


class ShoppingCartGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_case_class_model(model)
        self.generate_table_model(model)
        self.generate_repository_model(model)

    def generate_case_class_model(self, model):
        self.main_generator.generate("backend/app/model/case_class_shopping_cart_template.scala",
                                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep),
                                                  "model"),
                                     "ShoppingCart.scala",
                                     {"package": model.project.package})

    def generate_table_model(self, model):
        self.main_generator.generate("backend/app/repository/table/repository_table_shopping_cart_template.scala",
                                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep),
                                                  "repository", "table"),
                                     "ShoppingCartTable.scala",
                                     {"package": model.project.package})

    def generate_repository_model(self, model):
        self.main_generator.generate("backend/app/repository/repository_shopping_cart_template.scala",
                                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep),
                                                  "repository"),
                                     "ShoppingCartRepository.scala",
                                     {"package": model.project.package})
