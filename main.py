'''
Created on Jan 5, 2018

@author: Milorad Vojnovic
'''


import os

from generation.generators.controller_generator import ControllerGenerator
from generation.generators.dto_generator import DTOGenerator
from generation.generators.module_generator import ModuleGenerator
from generation.generators.service_generator import ServiceGenerator
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
    service_generator = ServiceGenerator(main_generator)
    controller_generator = ControllerGenerator(main_generator)
    dto_generator = DTOGenerator(main_generator)
    module_generator = ModuleGenerator(main_generator)
    main_generator.add_generator(model_generator)
    main_generator.add_generator(repository_generator)
    main_generator.add_generator(service_generator)
    main_generator.add_generator(controller_generator)
    main_generator.add_generator(dto_generator)
    main_generator.add_generator(module_generator)
    main_generator.generate_all(model)

