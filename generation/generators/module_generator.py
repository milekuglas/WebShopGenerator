'''
Created on Jan 9, 2018

@author: Milorad Vojnovic
'''


import os


from generation.generators.generator import Generator


class ModuleGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_module_model(model)
        self.generate_initial_data_model(model)

    def generate_module_model(self, model):
        self.main_generator.generate("module_template.scala",
                 os.path.join("app", model.project.package.name.replace(".", os.sep)), "Module.scala", {})

    def generate_initial_data_model(self, model):
        ids = {}
        index = 1
        for category in model.categories:
            ids[category.name] = index
            index += 1
        self.main_generator.generate("initial_data_template.scala",
                    os.path.join("app", model.project.package.name.replace(".", os.sep)), "InitialData.scala",
                                        {"model": model, "categories": model.categories, "ids" : ids})

