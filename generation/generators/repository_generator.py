'''
Created on Jan 5, 2018

@author: Milorad Vojnovic
'''


import os


from generation.generators.generator import Generator


class RepositoryGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_base_repository_model(model)
        self.generate_repository_model(model)
        self.generate_repository_full_model(model)

    def generate_base_repository_model(self, model):
        self.main_generator.generate("repository_template.scala",
                 os.path.join("app", model.project.package.name.replace(".", os.sep), "repository"),
                 model.base_product.name + "Repository.scala",
                 {"package": model.project.package, "product": model.base_product})

    def generate_repository_model(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate("repository_template.scala",
                     os.path.join("app", model.project.package.name.replace(".", os.sep), "repository"),
                     model.products[i].name + "Repository.scala",
                     {"package": model.project.package, "product": model.products[i],
                      "base_product": model.base_product})

    def generate_repository_full_model(self, model):
        for i in range(len(model.products)):
            self.main_generator.generate("repository_full_template.scala",
                     os.path.join("app", model.project.package.name.replace(".", os.sep), "repository"),
                     model.products[i].name + "FullRepository.scala",
                     {"package": model.project.package, "product": model.products[i],
                      "base_product": model.base_product})
