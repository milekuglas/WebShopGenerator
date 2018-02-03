'''
Created on Jan 5, 2018

@author: Milorad Vojnovic
'''


import os

from parsers.parser import Parser
from root import root
from generation.generators.conf_generator import ConfGenerator
from generation.generators.controller_generator import ControllerGenerator
from generation.generators.dto_generator import DTOGenerator
from generation.generators.module_generator import ModuleGenerator
from generation.generators.sbt_generator import SbtGenerator
from generation.generators.service_generator import ServiceGenerator
from generation.generators.main_generator import MainGenerator
from generation.generators.model_generator import ModelGenerator
from generation.generators.repository_generator import RepositoryGenerator
from generation.generators.table_generator import TableGenerator
from generation.generators.category_generator import CategoryGenerator


if __name__ == '__main__':
    parser = Parser()
    model = parser.parse(os.path.join(root, "metamodel"), 'scala-angular.tx', 'project.scan', True)
    main_generator = MainGenerator()
    model_generator = ModelGenerator(main_generator)
    table_generator = TableGenerator(main_generator)
    repository_generator = RepositoryGenerator(main_generator)
    service_generator = ServiceGenerator(main_generator)
    controller_generator = ControllerGenerator(main_generator)
    dto_generator = DTOGenerator(main_generator)
    module_generator = ModuleGenerator(main_generator)
    conf_generator = ConfGenerator(main_generator)
    sbt_generator = SbtGenerator(main_generator)
    category_generator = CategoryGenerator(main_generator)
    main_generator.add_generator(model_generator)
    main_generator.add_generator(repository_generator)
    main_generator.add_generator(service_generator)
    main_generator.add_generator(controller_generator)
    main_generator.add_generator(dto_generator)
    main_generator.add_generator(module_generator)
    main_generator.add_generator(conf_generator)
    main_generator.add_generator(sbt_generator)
    main_generator.add_generator(category_generator)
    main_generator.add_generator(table_generator)
    main_generator.generate_all(model)

