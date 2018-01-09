'''
Created on Jan 9, 2018

@author: Milorad Vojnovic
'''


import os


from generation.generators.generator import Generator


class ConfGenerator(Generator):

    def __init__(self, main_generator):
        self.main_generator = main_generator

    def generate(self, model):
        self.generate_conf_model(model)
        self.generate_routes_model(model)

    def generate_conf_model(self, model):
        self.main_generator.generate("conf_template.conf",
                 os.path.join("conf"), "application.conf",
                 {"model": model})

    def generate_routes_model(self, model):
        self.main_generator.generate("routes_template",
                 os.path.join("conf"), "routes",
                 {"model": model})

