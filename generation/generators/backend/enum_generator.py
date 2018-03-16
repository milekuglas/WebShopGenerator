'''
Created on Mar 16, 2018

@author: Milorad Vojnovic
'''

import os

from generation.generators.generator import Generator


class EnumGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_util(model)
        self.generate_model(model)

    def generate_util(self, model):
        self.main_generator.generate("backend/app/util/enum_util_template.scala",
                 os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "util"),
                 "EnumUtils.scala", {"model": model})

    def generate_model(self, model):
        for i in range(len(model.enums)):
            self.main_generator.generate("backend/app/model/enum_template.scala",
                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep), "model"),
                     model.enums[i].name + ".scala", {"model": model, "enum": model.enums[i]})
