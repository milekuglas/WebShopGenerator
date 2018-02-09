'''
Created on Feb 9, 2018

@author: Milorad Vojnovic
'''

import os

from generation.generators.generator import Generator


class UserGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_case_class_model(model)
        self.generate_table_model(model)
        self.generate_repository_model(model)

    def generate_case_class_model(self, model):
        self.main_generator.generate("backend/app/model/case_class_user_template.scala",
                                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep),
                                                  "model"),
                                     "User.scala",
                                     {"package": model.project.package})

    def generate_table_model(self, model):
        self.main_generator.generate("backend/app/repository/table/repository_table_user_template.scala",
                                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep),
                                                  "repository", "table"),
                                     "UserTable.scala",
                                     {"package": model.project.package})

    def generate_repository_model(self, model):
        self.main_generator.generate("backend/app/repository/repository_user_template.scala",
                                     os.path.join("backend", "app", model.project.package.name.replace(".", os.sep),
                                                  "repository"),
                                     "UserRepository.scala",
                                     {"package": model.project.package})
