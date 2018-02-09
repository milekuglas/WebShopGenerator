'''
Created on Jan 5, 2018

@author: Milorad Vojnovic
'''


import os

from generation.generators.backend.order_generator import OrderGenerator
from generation.generators.backend.order_item_generator import OrderItemGenerator
from generation.generators.backend.shopping_cart_generator import ShoppingCartGenerator
from generation.generators.backend.user_generator import UserGenerator
from parsers.parser import Parser
from root import root
from generation.generators.backend.conf_generator import ConfGenerator
from generation.generators.backend.controller_generator import ControllerGenerator
from generation.generators.backend.dto_generator import DTOGenerator
from generation.generators.backend.module_generator import ModuleGenerator
from generation.generators.backend.sbt_generator import SbtGenerator
from generation.generators.backend.service_generator import ServiceGenerator
from generation.generators.main_generator import MainGenerator
from generation.generators.backend.model_generator import ModelGenerator
from generation.generators.backend.repository_generator import RepositoryGenerator
from generation.generators.backend.table_generator import TableGenerator
from generation.generators.backend.category_generator import CategoryGenerator


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
    order_generator = OrderGenerator(main_generator)
    order_item_generator = OrderItemGenerator(main_generator)
    shopping_cart_generator = ShoppingCartGenerator(main_generator)
    user_generator = UserGenerator(main_generator)
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
    main_generator.add_generator(order_generator)
    main_generator.add_generator(order_item_generator)
    main_generator.add_generator(shopping_cart_generator)
    main_generator.add_generator(user_generator)
    main_generator.generate_all(model)

