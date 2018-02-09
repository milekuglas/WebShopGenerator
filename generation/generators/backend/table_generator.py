'''
Created on Feb 3, 2018

@author: Milorad Vojnovic
'''


import os


from generation.generators.generator import Generator


class TableGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_base_table_model(model)
        self.generate_table_model(model)

    def generate_base_table_model(self, model):
        self.main_generator.generate("backend/app/repository/table/repository_table_template.scala",
                 os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "repository", "table"),
                 model.base_product.name + "Table.scala",
                 {"package": model.project.package, "product": model.base_product})

    def generate_table_model(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate("backend/app/repository/table/repository_table_template.scala",
                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "repository", "table"),
                     model.products[i].name + "Table.scala",
                     {"package": model.project.package, "product": model.products[i],
                      "base_product": model.base_product})
