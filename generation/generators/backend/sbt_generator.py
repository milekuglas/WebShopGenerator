'''
Created on Jan 9, 2018

@author: Milorad Vojnovic
'''


import os


from generation.generators.generator import Generator


class SbtGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_build_sbt_model(model)
        self.generate_plugins_sbt_model()
        self.generate_properties_model()

    def generate_build_sbt_model(self, model):
        self.main_generator.generate("backend/build_sbt_template.sbt",
                 os.path.join("backend"), "build.sbt",
                 {"name": model.project.name})

    def generate_plugins_sbt_model(self):
        self.main_generator.generate("backend/project/plugins_sbt_template.sbt",
                 os.path.join("backend", "project"), "plugins.sbt", {})

    def generate_properties_model(self):
        self.main_generator.generate("backend/project/build_template.properties",
                 os.path.join("backend", "project"), "build.properties", {})

