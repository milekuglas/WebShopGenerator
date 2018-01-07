'''
Created on Jan 5, 2018

@author: Milorad Vojnovic
'''


import os

from parsers.parser import Parser
from root import root
from generation.generators.main_generator import MainGenerator
from generation.generators.model_generator import ModelGenerator
from generation.generators.repository_generator import RepositoryGenerator


if __name__ == '__main__':
    parser = Parser()
    model = parser.parse(os.path.join(root, "metamodel"), 'scala-angular.tx', 'project.scan', True)
    main_generator = MainGenerator()
    model_generator = ModelGenerator(main_generator)
    repository_generator = RepositoryGenerator(main_generator)
    main_generator.add_generator(model_generator)
    main_generator.add_generator(repository_generator)
    main_generator.generate_all(model)